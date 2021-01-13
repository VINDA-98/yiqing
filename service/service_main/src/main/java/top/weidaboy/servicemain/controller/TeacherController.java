package top.weidaboy.servicemain.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.servicemain.entity.Student;
import top.weidaboy.servicemain.entity.Teacher;
import top.weidaboy.servicemain.query.TeacherQuery;
import top.weidaboy.servicemain.service.*;

import java.util.List;

@RestController
@RequestMapping("/servicemain/teacher")
@CrossOrigin //跨域
@Api(description="教师控制类")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    CollegeService collegeService;

    @ApiOperation(value = "教师分页列表")
    @PostMapping("{page}/{limit}")
    public R pagelist(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页的记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询教师对象", required = false)
            @RequestBody TeacherQuery teacherQuery){
        //新建page对象
        Page<Teacher> pageParam = new Page<>(page, limit);
        //查询当前页
        teacherService.pageQuery(pageParam, teacherQuery);
        //获取所有记录 records：记录
        List<Teacher> records = pageParam.getRecords();
        for (Teacher record : records) {
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加
    @ApiOperation(value = "新增教师")
    @PostMapping("/save")
    public R save(
            @ApiParam(name = "teacher", value = "教师对象", required = true)
            @RequestBody Teacher teacher){
        teacherService.save(teacher);
        return R.ok().data("teacher", teacher);
    }

    //删除
    @ApiOperation(value = "根据ID删除教师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "教师ID", required = true)
            @PathVariable String id){
        boolean result = teacherService.removeById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    //教师修改功能
    @PostMapping("updateteacher")
    public R updateTeacher(@RequestBody Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if(flag) {
            return R.ok().data("teacher", teacher);
        } else {
            return R.error();
        }
    }


    //根据ID查询
    @ApiOperation(value = "根据ID查询教师")
    @GetMapping("/getteacher/{id}")
    public R getById(
            @ApiParam(name = "id", value = "教师ID", required = true)
            @PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

}
