package top.weidaboy.servicemain.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.weidaboy.commonutils.R;
import top.weidaboy.servicemain.entity.Admin;
import top.weidaboy.servicemain.service.AdminService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
@Api(description="管理员控制类")
@RestController
@CrossOrigin //跨域
@RequestMapping("/servicemain/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "所有管理员列表")
    @GetMapping
    public R adminList(){
        //int a = 0/0;
        List<Admin> list = adminService.list(null);
        return  R.ok().data("items",list);
    }

    @ApiOperation(value = "根据ID删除管理员")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "管理员ID", required = true)
            @PathVariable String id){
        adminService.removeById(id);
        return R.ok();
    }
}

