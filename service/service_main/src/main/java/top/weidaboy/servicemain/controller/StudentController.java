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
import top.weidaboy.servicemain.query.StudentQuery;
import top.weidaboy.servicemain.service.ClassService;
import top.weidaboy.servicemain.service.CollegeService;
import top.weidaboy.servicemain.service.MajorService;
import top.weidaboy.servicemain.service.StudentService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
@Api(description="学生控制类")
@RestController
@RequestMapping("/servicemain/student")
@CrossOrigin //跨域
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    ClassService classService;
    @Autowired
    MajorService majorService;
    @Autowired
    CollegeService collegeService;

    @ApiOperation(value = "学生分页列表")
    @PostMapping("{page}/{limit}")
    public R pagelist(
            @ApiParam(name = "page",value = "当前页码",required = true)
              @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页的记录数",required = true)
              @PathVariable Long limit,
            @ApiParam(name = "studentQuery", value = "查询学生对象", required = false)
            @RequestBody StudentQuery studentQuery){
        //新建page对象
        Page<Student> pageParam = new Page<>(page, limit);
        //查询当前页
        studentService.pageQuery(pageParam, studentQuery);
        //获取所有记录 records：记录
        List<Student> records = pageParam.getRecords();
        for (Student record : records) {
            String classname = classService.classNameByID(record.getClassid());
            record.setClassid(classname);
            String majorname = majorService.majorNameByID(record.getMajorid());
            record.setMajorid(majorname);
            String collegename = collegeService.collegeNameByID(record.getCollegeid());
            record.setCollegeid(collegename);
        }
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加
    @ApiOperation(value = "新增学生")
    @PostMapping("/save")
    public R save(
            @ApiParam(name = "student", value = "学生对象", required = true)
            @RequestBody Student student){
        studentService.save(student);
        return R.ok().data("student", student);
    }

    //删除
    @ApiOperation(value = "根据ID删除学生")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "学生ID", required = true)
            @PathVariable String id){
        boolean result = studentService.removeById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    //学生修改功能
    @PostMapping("updatestudent")
    public R updateStudent(@RequestBody Student student) {
        boolean flag = studentService.updateById(student);
        if(flag) {
            return R.ok().data("student", student);
        } else {
            return R.error();
        }
    }


    //根据ID查询
    @ApiOperation(value = "根据ID查询学生")
    @GetMapping("/getstudent/{id}")
    public R getById(
            @ApiParam(name = "id", value = "学生ID", required = true)
            @PathVariable String id){
        Student student = studentService.getById(id);
        return R.ok().data("student", student);
    }



}

