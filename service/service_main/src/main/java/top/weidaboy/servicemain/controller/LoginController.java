package top.weidaboy.servicemain.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;

@RestController
@RequestMapping("/servicemain/user")
@CrossOrigin  //解决跨域
@Api(description="用户登录")
public class LoginController {

    //login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }
    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","Vinda").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
