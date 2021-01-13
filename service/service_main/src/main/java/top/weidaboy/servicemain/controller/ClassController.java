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
import top.weidaboy.servicemain.entity.Student;
import top.weidaboy.servicemain.query.ClassQuery;
import top.weidaboy.servicemain.query.MajorQuery;
import top.weidaboy.servicemain.service.ClassService;
import top.weidaboy.servicemain.service.CollegeService;
import top.weidaboy.servicemain.service.MajorService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
@Api(description="班级控制类")
@RestController
@RequestMapping("/servicemain/class")
@CrossOrigin //跨域
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    CollegeService collegeService;

    @Autowired
    MajorService majorService;

    //添加
    @ApiOperation(value = "新增班级")
    @PostMapping("/save")
    public R save(
            @ApiParam(name = "myclass", value = "班级对象", required = true)
            @RequestBody Class myclass){
        classService.save(myclass);
        return R.ok().data("myclass", myclass);
    }

    //删除
    @ApiOperation(value = "根据ID删除班级")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "班级ID", required = true)
            @PathVariable String id){
        boolean result = classService.removeById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    //班级修改功能
    @PostMapping("updateclass")
    public R updateStudent(@RequestBody Class myclass) {
        boolean flag = classService.updateById(myclass);
        if(flag) {
            return R.ok().data("myclass", myclass);
        } else {
            return R.error();
        }
    }

    //根据ID查询
    @ApiOperation(value = "根据ID查询班级")
    @GetMapping("/getclass/{id}")
    public R getById(
            @ApiParam(name = "id", value = "班级ID", required = true)
            @PathVariable String id){
        Class myclass = classService.getById(id);
        return R.ok().data("classbyid", myclass);
    }

    //根据学院ID查询
    @ApiOperation(value = "根据学院ID查询班级")
    @GetMapping("/getclassbycollegeid/{id}")
    public R getByCollegeId(
            @ApiParam(name = "id", value = "学院ID", required = true)
            @PathVariable String id){
        List<Class> myclass = classService.getClassByCollegeid(id);
        return R.ok().data("myclass", myclass);
    }

    //根据专业ID查询
    @ApiOperation(value = "根据专业ID查询班级")
    @GetMapping("/getclassbymajorid/{id}")
    public R getByMajorID(
            @ApiParam(name = "id", value = "班级ID", required = true)
            @PathVariable String id){
        List<Class> myclass = classService.getClassByMajorID(id);
        return R.ok().data("myclass", myclass);
    }

    //根据班级ID得到对应专业和对应学院
    @ApiOperation(value = "根据班级ID查询学院和专业")
    @GetMapping("/getbycollegeandmajorbyclassid/{id}")
    public R getByCollegeAndMajorByClassID(
            @ApiParam(name = "id", value = "班级ID", required = true)
            @PathVariable String id){
        //获得对应的班级
        Class classServiceById = classService.getById(id);
        //查询专业
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",classServiceById.getMajorid());
        Major major = majorService.getOne(queryWrapper);
        //查询学院
        QueryWrapper<College> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("id",classServiceById.getMajorid());
        College college = collegeService.getOne(queryWrapper1);

        List<Major> majors = new ArrayList<>();
        majors.add(major);

        List<College> colleges = new ArrayList<>();
        colleges.add(college);
        return R.ok().data("majors", majors).data("colleges",colleges);
    }

    //获取所有专业
    @ApiOperation(value = "获取所有班级信息")
    @GetMapping("/getallclass")
    public R getAllCollege(){
        List<Class> myclass = classService.getAllClass();
        return R.ok().data("myclass", myclass);
    }

    @ApiOperation(value = "班级分页列表")
    @PostMapping("{page}/{limit}")
    public R pagelist(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页的记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "classQuery", value = "查询班级对象", required = false)
            @RequestBody ClassQuery classrQuery){
        //新建page对象
        Page<Class> pageParam = new Page<>(page, limit);
        //查询当前页
        classService.pageQuery(pageParam, classrQuery);
        //获取所有记录 records：记录
        List<Class> records = pageParam.getRecords();
        for (Class myclass : records) {
            String collegename = collegeService.collegeNameByID(myclass.getCollegeid());
            myclass.setCollegeid(collegename);
            String majorName = majorService.majorNameByID(myclass.getMajorid());
            myclass.setMajorid(majorName);
        }
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }


}

