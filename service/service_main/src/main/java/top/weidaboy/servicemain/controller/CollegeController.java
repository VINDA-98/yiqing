package top.weidaboy.servicemain.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.servicemain.entity.College;
import top.weidaboy.servicemain.query.CollegeQuery;
import top.weidaboy.servicemain.service.CollegeService;

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
@Api(description="学院控制类")
@RestController
@RequestMapping("/servicemain/college")
@CrossOrigin //跨域
public class CollegeController {

    @Autowired
    CollegeService collegeService;

    //添加
    @ApiOperation(value = "新增学院")
    @PostMapping("/save")
    public R save(
            @ApiParam(name = "college", value = "学院对象", required = true)
            @RequestBody College college){
        System.out.println(college);
        collegeService.save(college);
        return R.ok().data("college", college);
    }

    //删除
    @ApiOperation(value = "根据ID删除学院")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "学院ID", required = true)
            @PathVariable String id){
        boolean result = collegeService.removeById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    //学院修改功能
    @PostMapping("updatecollege")
    public R updateCollege(@RequestBody College college) {
        boolean flag = collegeService.updateById(college);
        if(flag) {
            return R.ok().data("college", college);
        } else {
            return R.error();
        }
    }

    //根据ID查询
    @ApiOperation(value = "根据ID查询学院")
    @GetMapping("/getcollege/{id}")
    public R getById(
            @ApiParam(name = "id", value = "学院ID", required = true)
            @PathVariable String id){
        College college = collegeService.getById(id);
        return R.ok().data("College", college);
    }

    //获取所有学院
    @ApiOperation(value = "获取所有学院信息")
    @GetMapping("/getallcollege")
    public R getAllCollege(){
        List<College> colleges = collegeService.getAllCollege();
        return R.ok().data("colleges", colleges);
    }

    @ApiOperation(value = "学院分页列表")
    @PostMapping("{page}/{limit}")
    public R pagelist(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页的记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "collegeQuery", value = "查询学院对象", required = false)
            @RequestBody CollegeQuery collegeQuery){
        //新建page对象
        Page<College> pageParam = new Page<>(page, limit);
        //查询当前页
        collegeService.pageQuery(pageParam, collegeQuery);
        //获取所有记录 records：记录
        List<College> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    //获取学校总人数，各个学院名称，各个学院人数，各个学院教师人数，各个学院学生人数
    @ApiOperation(value = "人数列表")
    @PostMapping("/getallpeople")
    public R getAllPeopel(){
        List<CollegeQuery> allCollege = collegeService.getAllPeopel();
        //获取所有学院名称
        int psum = 0;
        List<String> collegenames = new ArrayList<>();
        for (CollegeQuery college : allCollege) {
                collegenames.add(college.getName());
                psum += Integer.parseInt(college.getSum());
        }
        return R.ok().data("allCollege",allCollege).data("collegenames",collegenames).data("psum",psum);
    }
}

