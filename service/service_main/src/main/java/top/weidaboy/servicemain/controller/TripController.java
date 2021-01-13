package top.weidaboy.servicemain.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import top.weidaboy.servicemain.entity.Trip;
import top.weidaboy.servicemain.query.TempQuery;
import top.weidaboy.servicemain.query.TripQuery;
import top.weidaboy.servicemain.service.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vinda
 * @since 2020-12-28
 */
@Api(description="行程控制类")
@RestController
@RequestMapping("/servicemain/trip")
@CrossOrigin
public class TripController {
    @Autowired
    TripService tripService;

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
    MSMClient msmClient;


    //生成随机测试温度数据
    @ApiOperation("测试方法")
    @GetMapping("/test")
    public R test() {
        return R.ok().data("romdowtrip",tripService.getRomdomAddress());
    }

    //获取异常行程
    @ApiOperation("获取异常行程")
    @GetMapping("/geterrorTrip")
    public R getErrorTrip(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "mode", value = "查询模式", required = true)
            @PathVariable Long mode,
            @ApiParam(name = "tripQuery", value = "查询温度对象", required = false)
            @RequestBody TripQuery tripQuery
            ) {
        //新建page对象
        Page<TripQuery> pageParam = new Page<>(page, limit);
        List<String> strings = null;
        QueryWrapper<TripQuery> queryQueryWrapper = new QueryWrapper<>();
        queryQueryWrapper.like("trip",tripQuery);

        IPage<TripQuery> studentNewTrip = null;
        //通过模式查询当前页
        if (mode == 1) studentNewTrip = tripService.getStudentAllTrip(pageParam, tripQuery);
        else {
            studentNewTrip = tripService.getStudentNewTrip(pageParam, tripQuery);
        }
        long total = studentNewTrip.getTotal();
        long pages = studentNewTrip.getPages();
        List<TripQuery> records = studentNewTrip.getRecords();
        //获取所有记录 records：记录
        for (TripQuery record : records) {
            String classname = classService.classNameByID(record.getClassid());
            record.setClassid(classname);
            String majorname = majorService.majorNameByID(record.getMajorid());
            record.setMajorid(majorname);
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        return R.ok().data("total", total).data("rows", records);
    }


    //生成随机测试温度数据
    @ApiOperation("获取随机行程")
    @GetMapping("/getRandomTrip")
    public R getRandomTemp() {
        List<Trip> trips = new ArrayList<>();
        List<Student> students = studentService.list(null);
        List<Teacher> teachers = teacherService.list(null);
        Random random = new Random();
        for (Student student : students) {
            QueryWrapper<Trip> queryWrapper = new QueryWrapper<>();//添加查询条件
            Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
            int rannum = (int) (random.nextDouble() * (99 - 10 + 1)) + 10;// 获取2位随机数
            queryWrapper.orderByDesc("gmt_tripend"); //按照时间降序排列

            queryWrapper.eq("name", student.getName());//获得用户姓名
            Format dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            queryWrapper.like("gmt_tripend", dateFormat1.format(new Date()));//获得上一次修改时间
            Trip one = tripService.getOne(queryWrapper);

            //如果存在，是同一天数据
            if (null != one) {
                one.setTrip(tripService.getRomdomAddress());
                one.setGmtTripend(new Date()); //更新最后时间
                tripService.update(one, queryWrapper);
            }//如果不存在，插入新数据
            else {
                Trip trip = new Trip();
                trip.setId(dateFormat.format(new Date()) + rannum);
                trip.setUserid(student.getId());
                trip.setName(student.getName());
                trip.setCollegeid(student.getCollegeid());
                trip.setMajorid(student.getMajorid());
                trip.setClassid(student.getClassid());
                trip.setTrip(tripService.getRomdomAddress());
                tripService.save(trip);
            }
        }
        for (Teacher teacher : teachers) {
            QueryWrapper<Trip> queryWrapper = new QueryWrapper<>();//添加查询条件
            Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
            int rannum = (int) (random.nextDouble() * (99 - 10 + 1)) + 10;// 获取2位随机数
            queryWrapper.orderByDesc("gmt_tripend"); //按照时间降序排列

            queryWrapper.eq("name", teacher.getName());//获得用户姓名
            Format dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            queryWrapper.like("gmt_tripend", dateFormat1.format(new Date()));//获得上一次修改时间
            Trip one = tripService.getOne(queryWrapper);

            //如果存在，是同一天数据
            if (null != one) {
                one.setTrip(tripService.getRomdomAddress());
                one.setGmtTripend(new Date()); //更新最后时间
                tripService.update(one, queryWrapper);
            }//如果不存在，插入新数据
            else {
                Trip trip = new Trip();
                trip.setId(dateFormat.format(new Date()) + rannum);
                trip.setUserid(teacher.getId());
                trip.setName(teacher.getName());
                trip.setCollegeid(teacher.getCollegeid());
                trip.setTrip(tripService.getRomdomAddress());
                tripService.save(trip);
            }
        }
        return R.ok().data("trips", trips);
    }

    @ApiOperation(value = "原型学生行程分页列表")
    @PostMapping("/studentmode/{page}/{limit}/{mode}")
    public R pageListStudent(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "mode", value = "查询模式", required = true)
            @PathVariable Long mode,
            @ApiParam(name = "tripQuery", value = "查询温度对象", required = false)
            @RequestBody TripQuery tripQuery) {
        //新建page对象
        Page<TripQuery> pageParam = new Page<>(page, limit);
        IPage<TripQuery> studentNewTrip = null;
        //通过模式查询当前页
        if (mode == 1) studentNewTrip = tripService.getStudentAllTrip(pageParam, tripQuery);
        else {
            studentNewTrip = tripService.getStudentNewTrip(pageParam, tripQuery);
        }
        long total = studentNewTrip.getTotal();
        long pages = studentNewTrip.getPages();
        List<TripQuery> records = studentNewTrip.getRecords();
        //获取所有记录 records：记录
        for (TripQuery record : records) {
            String classname = classService.classNameByID(record.getClassid());
            record.setClassid(classname);
            String majorname = majorService.majorNameByID(record.getMajorid());
            record.setMajorid(majorname);
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "原型教师行程分页列表")
    @PostMapping("/teachermode/{page}/{limit}/{mode}")
    public R pageListTeacher(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "mode", value = "查询模式", required = true)
            @PathVariable Long mode,
            @ApiParam(name = "tripQuery", value = "查询温度对象", required = false)
            @RequestBody TripQuery tripQuery) {
        //新建page对象
        Page<TripQuery> pageParam = new Page<>(page, limit);
        IPage<TripQuery> studentNewTrip = null;
        //通过模式查询当前页
        if (mode == 1) studentNewTrip = tripService.getTeacherAllTrip(pageParam, tripQuery);
        else {
            studentNewTrip = tripService.getTeacherNewTrip(pageParam, tripQuery);
        }
        long total = studentNewTrip.getTotal();
        long pages = studentNewTrip.getPages();
        List<TripQuery> records = studentNewTrip.getRecords();
        //获取所有记录 records：记录
        for (TripQuery record : records) {
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "查询各个学院更新行程信息的总人数")
    @PostMapping("/getRefreshTrip")
    public R getRefreshTemp() {
        List<TripQuery> refreshTrip = tripService.getRefreshTrip();
        return R.ok().data("refreshTrip", refreshTrip);
    }

    //发送邮件
    @ApiOperation("发送行程警告邮件")
    @PostMapping("/sendAsyncEmail/{id}")
    public R sendEmail(
            @ApiParam(name = "id", value = "对应的行程ID", required = true)
            @PathVariable String id
    ) throws Exception {
        //获取对应的行程信息
        Trip trip = tripService.getById(id);
        String subject = "行程异常提醒 from 百色学院";
        Format dateFormat = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        String content = "    尊敬的" + trip.getName() + "，您好！您于" + dateFormat.format(trip.getGmtTripend())
                + "更新提交的行程信息，确认为新增疫情人数或确诊新冠疫情人数较多的城市，请您配合到我校校医处进行相关治理，并且" +
                "自行隔离14天，人人做好一小步，疫情远离一大步！";
        Student student = studentService.getById(trip.getUserid());
        String setToPerpel = student.getEmail();
        //修改状态
        trip.setTripreslove(0);
        tripService.updateById(trip);//更新状态
        return msmClient.sendAsyncEmail(subject, content, setToPerpel);
    }


    //发送邮件
    @ApiOperation("发送行程更新邮件")
    @PostMapping("/sendRefreshEmail/{id}")
    public R sendRefEmail(
            @ApiParam(name = "id", value = "对应的行程ID", required = true)
            @PathVariable String id
    ) throws Exception {
        //获取对应的行程信息
        Trip trip = tripService.getById(id);
        String subject = "行程更新提醒 from 百色学院";
        Format dateFormat = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        String content = "    尊敬的" + trip.getName() + "，您好！您截止" + dateFormat.format(trip.getGmtTripend())
                + "，尚未更新您的相关行程信息到我校疫情管理系统，请您尽快更新行程信息。如已您更新，请忽略本次通知";
        Student student = studentService.getById(trip.getUserid());
        String setToPerpel = student.getEmail();
        //修改状态
        trip.setTripreslove(0);
        tripService.updateById(trip);//更新状态
        return msmClient.sendAsyncEmail(subject, content, setToPerpel);
    }
}

