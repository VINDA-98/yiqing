package top.weidaboy.servicemain.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.client.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.servicemain.client.MQTTclient;
import top.weidaboy.servicemain.client.MSMClient;
import top.weidaboy.servicemain.entity.Student;
import top.weidaboy.servicemain.entity.Teacher;
import top.weidaboy.servicemain.entity.Temp;
import top.weidaboy.servicemain.query.TempQuery;
import top.weidaboy.servicemain.service.*;
import top.weidaboy.servicemain.util.PinYin;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author vinda
 * @since 2020-12-26
 */
@Api(description="温度控制类")
@RestController
@RequestMapping("/servicemain/temp")
@CrossOrigin
public class TempController {

    @Autowired
    TempService tempService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    ClassService classService;

    @Autowired
    MajorService majorService;

    @Autowired
    CollegeService collegeService;

    @Autowired
    MQTTclient mqtTclient;

    @Autowired
    MSMClient msmClient;


    //发送邮件
    @ApiOperation("发送温度警告邮件")
    @PostMapping("/sendAsyncEmail/{id}")
    public R sendEmail(
            @ApiParam(name = "id", value = "对应的温度ID", required = true)
            @PathVariable String id
    ) throws Exception {
        //获取对应的温度信息
        System.out.println("sendID:"+id);
        Temp temp = tempService.getById(id);
        String subject = "体温异常提醒 from 百色学院";
        Format dateFormat = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        String content = "    尊敬的" + temp.getName() + "，您好！您于" + dateFormat.format(temp.getGmtTempend())
                + "被我校设备监测到体温异常，请您尽快到我校校医处进行相关治疗，如需进行核酸检测，请您到附近医院进行就诊";
        Student student = studentService.getById(temp.getUserid());
        String setToPerpel = student.getEmail();
        //修改状态
        temp.setTempresolve(0);
        tempService.updateById(temp);//更新状态
        return msmClient.sendAsyncEmail(subject, content, setToPerpel);
    }


    //发送邮件
    @ApiOperation("发送温度更新邮件")
    @PostMapping("/sendRefreshEmail/{id}")
    public R sendRefEmail(
            @ApiParam(name = "id", value = "对应的温度ID", required = true)
            @PathVariable String id
    ) throws Exception {
        //获取对应的温度信息
        Temp temp = tempService.getById(id);
        String subject = "体温更新 from 百色学院";
        Format dateFormat = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        String content = "    尊敬的" + temp.getName() + "，您好！您今日（截止" + dateFormat.format(temp.getGmtTempend())
                + "）的体温记录尚未更新到学校系统，请您尽快更新体温信息。如已您更新，请忽略本次通知";
        Student student = studentService.getById(temp.getUserid());
        String setToPerpel = student.getEmail();
        //修改状态
        temp.setTempresolve(0);
        tempService.updateById(temp);//更新状态
        return msmClient.sendAsyncEmail(subject, content, setToPerpel);
    }


    //发送指令
    @ApiOperation("发送指令到单片机")
    @GetMapping("/send/{item}/{data}")
    public R sendData(
            @ApiParam(name = "item", value = "发送给单片机的指令", required = true)
            @PathVariable String item,
            @ApiParam(name = "data", value = "发送给单片机的指令内容", required = true)
            @PathVariable String data
    ) throws ClientException {
        System.out.println("item:"+item);
        System.out.println("data:"+data);
        if(item != null && item.equals("photo")){
            PinYin p = new PinYin();
            String name = p.StrToPinYin(data);
            System.out.println("name:"+name);
            return mqtTclient.sendMqtt(item, name);
        }
        return mqtTclient.sendMqtt(item, data);
    }

    //生成随机测试温度数据
    @ApiOperation("获取随机温度")
    @GetMapping("/getRandomTemp")
    public R getRandomTemp() {
        List<Temp> temps = new ArrayList<>();
        List<Student> students = studentService.list(null);
        List<Teacher> teachers = teacherService.list(null);
        for (Student student : students) {
            QueryWrapper<Temp> queryWrapper = new QueryWrapper<>();//添加查询条件
            Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
            Random random = new Random();
            int rannum = (int) (random.nextDouble() * (99 - 10 + 1)) + 10;// 获取2位随机数
            Double tempxiaoshu = random.nextDouble() + 36;// 获取1位随机数  0-1 的小数
            queryWrapper.orderByDesc("gmt_tempend"); //按照时间降序排列
            queryWrapper.eq("name", student.getName());//获得用户姓名
            Format dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            queryWrapper.like("gmt_tempend", dateFormat1.format(new Date()));//获得上一次修改时间
            Temp one = tempService.getOne(queryWrapper);

            //如果存在，是同一天数据
            if (null != one) {
                one.setTemp(String.format("%.1f", tempxiaoshu));
                one.setGmtTempend(new Date()); //更新最后时间
                tempService.update(one, queryWrapper);
            }//如果不存在，插入新数据
            else {
                Temp temp = new Temp();
                temp.setId(dateFormat.format(new Date()) + rannum);
                temp.setUserid(student.getId());
                temp.setName(student.getName());
                temp.setCollegeid(student.getCollegeid());
                temp.setMajorid(student.getMajorid());
                temp.setClassid(student.getClassid());
                temp.setTemp(String.format("%.1f", tempxiaoshu));
                tempService.save(temp);
            }
        }
        for (Teacher teacher : teachers) {
            QueryWrapper<Temp> queryWrapper = new QueryWrapper<>();
            ;  //添加查询条件
            Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
            Random random = new Random();
            int rannum = (int) (random.nextDouble() * (99 - 10 + 1)) + 10;// 获取2位随机数
            Double tempxiaoshu = random.nextDouble() + 36;// 获取1位随机数  0-1 的小数
            queryWrapper.orderByDesc("gmt_tempend"); //按照时间降序排列
            queryWrapper.eq("name", teacher.getName());//获得用户姓名
            Format dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            queryWrapper.like("gmt_tempend", dateFormat1.format(new Date()));//获得上一次修改时间
            Temp one = tempService.getOne(queryWrapper);

            //如果存在，是同一天数据
            if (null != one) {
                one.setTemp(String.format("%.1f", tempxiaoshu));
                one.setGmtTempend(new Date()); //更新最后时间
                tempService.update(one, queryWrapper);
            }//如果不存在，插入新数据
            else {
                Temp temp = new Temp();
                temp.setId(dateFormat.format(new Date()) + rannum);
                temp.setUserid(teacher.getId());
                temp.setName(teacher.getName());
                temp.setCollegeid(teacher.getCollegeid());
                temp.setMajorid("无");
                temp.setClassid("无");
                temp.setTemp(String.format("%.1f", tempxiaoshu));
                tempService.save(temp);
            }
        }
        //tempService.saveBatch(temps);
        return R.ok().data("temps", temps);
    }


    //获得所有温度信息
    @GetMapping("/getalltemp")
    public R getAllTemp() {
        return R.ok();
    }

    //获得同个用户的所有数据
    @ApiOperation("获得同个用户的所有数据")
    @PostMapping("/getuseralltemp/{page}/{limit}/{id}")
    public R getUserAllTemp(
            @ApiParam(name = "id", value = "用户ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable Long limit) {
        //新建page对象
        Page<Temp> pageParam = new Page<>(page, limit);
        tempService.getUserTemp(pageParam, id);
        List<Temp> records = pageParam.getRecords();
        String classname = classService.classNameByID(records.get(0).getClassid());
        String majorname = classService.classNameByID(records.get(0).getMajorid());
        String collegename = classService.classNameByID(records.get(0).getCollegeid());
        for (Temp record : records) {
            record.setClassid(classname);
            record.setMajorid(majorname);
            record.setCollegeid(collegename);
        }
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }


    //获得所有学生温度信息
    @ApiOperation(value = "获得学生模式温度信息")
    @PostMapping("/studentmode/{page}/{limit}/{mode}")
    public R getAllStudentModeTemp(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "mode", value = "查询模式", required = true)
            @PathVariable Long mode,
            @ApiParam(name = "tempQuery", value = "查询温度对象", required = false)
            @RequestBody TempQuery tempQuery
    ) {
        Page<TempQuery> pageParam = new Page<>(page, limit);
        IPage<TempQuery> studentModeTemp = null;
        if (mode == 1) studentModeTemp = tempService.getStudentAllTemp(pageParam, tempQuery);
        else studentModeTemp = tempService.getStudentNewTemp(pageParam, tempQuery);

        long total = studentModeTemp.getTotal();
        long pages = studentModeTemp.getPages();
        List<TempQuery> records = studentModeTemp.getRecords();

        //获取所有记录 records：记录
        for (TempQuery record : records) {
            String classname = classService.classNameByID(record.getClassid());
            record.setClassid(classname);
            String majorname = majorService.majorNameByID(record.getMajorid());
            record.setMajorid(majorname);
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        return R.ok().data("total", total).data("pages", pages).data("rows", records);
    }


    //获得所有教师温度信息
    @ApiOperation(value = "获得教师模式温度信息")
    @PostMapping("/teachermode/{page}/{limit}/{mode}")
    public R getAllTeacherModeTemp(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "mode", value = "查询模式", required = true)
            @PathVariable Long mode,
            @ApiParam(name = "tempQuery", value = "查询温度对象", required = false)
            @RequestBody TempQuery tempQuery
    ) {
        Page<TempQuery> pageParam = new Page<>(page, limit);
        IPage<TempQuery> tempQueryIPage = null;
        if (mode == 1) tempQueryIPage = tempService.getTeacherAllTemp(pageParam, tempQuery);
        else tempQueryIPage = tempService.getTeacherNewTemp(pageParam, tempQuery);
        long total = tempQueryIPage.getTotal();
        long pages = tempQueryIPage.getPages();
        List<TempQuery> records = tempQueryIPage.getRecords();
        //获取所有记录 records：记录
        for (TempQuery record : records) {
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        return R.ok().data("total", total).data("pages", pages).data("rows", records);
    }


    @ApiOperation(value = "原型温度分页列表")
    @PostMapping("{page}/{limit}")
    public R pagelist(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "tempQuery", value = "查询温度对象", required = false)
            @RequestBody TempQuery tempQuery) {
        //新建page对象
        Page<Temp> pageParam = new Page<>(page, limit);
        //查询当前页
        tempService.pageQuery(pageParam, tempQuery);
        //获取所有记录 records：记录
        List<Temp> records = pageParam.getRecords();
        for (Temp record : records) {
            String classname = classService.classNameByID(record.getClassid());
            record.setClassid(classname);
            String majorname = majorService.majorNameByID(record.getMajorid());
            record.setMajorid(majorname);
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "查询各个学院更新温度信息的总人数")
    @PostMapping("/getRefreshTemp")
    public R getRefreshTemp() {
        List<TempQuery> refreshTemp = tempService.getRefreshTemp();
        return R.ok().data("refreshTemp", refreshTemp);
    }
}

