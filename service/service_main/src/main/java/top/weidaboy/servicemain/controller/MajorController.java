package top.weidaboy.servicemain.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.servicemain.entity.Class;
import top.weidaboy.servicemain.entity.College;
import top.weidaboy.servicemain.entity.Major;
import top.weidaboy.servicemain.query.MajorQuery;
import top.weidaboy.servicemain.service.CollegeService;
import top.weidaboy.servicemain.service.MajorService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author vinda
 * @since 2020-12-07
 */
@Api(description="专业控制类")
@RestController
@RequestMapping("/servicemain/major")
@CrossOrigin //跨域
public class MajorController {

    @Autowired
    MajorService majorService;

    @Autowired
    CollegeService collegeService;

    //添加
    @ApiOperation(value = "新增专业")
    @PostMapping("/save")
    public R save(
            @ApiParam(name = "student", value = "专业对象", required = true)
            @RequestBody Major major){
        majorService.save(major);
        return R.ok().data("major", major);
    }

    //删除
    @ApiOperation(value = "根据ID删除专业")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "专业ID", required = true)
            @PathVariable String id){
        boolean result = majorService.removeById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    //专业修改功能
    @PostMapping("updatemajor")
    public R updateStudent(@RequestBody Major major) {
        boolean flag = majorService.updateById(major);
        if(flag) {
            return R.ok().data("major", major);
        } else {
            return R.error();
        }
    }

    //根据ID查询
    @ApiOperation(value = "根据ID查询专业")
    @GetMapping("/getmajor/{id}")
    public R getById(
            @ApiParam(name = "id", value = "专业ID", required = true)
            @PathVariable String id){
        Major major = majorService.getById(id);
        return R.ok().data("major", major);
    }

    //根据学院ID查询
    @ApiOperation(value = "根据学院ID查询专业")
    @GetMapping("/getmajorbycollegeid/{id}")
    public R getByCollegeId(
            @ApiParam(name = "id", value = "学院ID", required = true)
            @PathVariable String id){
        List<Major> majors = majorService.getMajorByCollegeid(id);
        return R.ok().data("majors", majors);
    }

    //获取所有专业
    @ApiOperation(value = "获取所有学院信息")
    @GetMapping("/getallmajor")
    public R getAllCollege(){
        List<Major> majors = majorService.getAllMajor();
        return R.ok().data("majors", majors);
    }


    //根据班级ID得到对应专业和对应学院
    @ApiOperation(value = "根据专业ID查询学院")
    @GetMapping("/getbycollegebymajorid/{id}")
    public R getByCollegeByMajorID(
            @ApiParam(name = "id", value = "专业ID", required = true)
            @PathVariable String id){
        //获得对应的班级
        Major majorServiceById = majorService.getById(id);
        //查询学院
        QueryWrapper<College> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",majorServiceById.getCollegeid());
        College college = collegeService.getOne(queryWrapper);
        List<College> colleges = new ArrayList<>();
        colleges.add(college);
        return R.ok().data("colleges",colleges);
    }

    @ApiOperation(value = "专业分页列表")
    @PostMapping("{page}/{limit}")
    public R pagelist(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页的记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "majorQuery", value = "查询专业对象", required = false)
            @RequestBody MajorQuery majorQuery){
        //新建page对象
        Page<Major> pageParam = new Page<>(page, limit);
        //查询当前页
        majorService.pageQuery(pageParam, majorQuery);
        //获取所有记录 records：记录
        List<Major> records = pageParam.getRecords();
        for (Major record : records) {
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }


}

