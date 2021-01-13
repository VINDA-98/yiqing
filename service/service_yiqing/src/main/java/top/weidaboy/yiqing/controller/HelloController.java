package top.weidaboy.yiqing.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.weidaboy.commonutils.R;
import top.weidaboy.yiqing.model.HelloVO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/serviceyiqing/")
@CrossOrigin //跨域
public class HelloController {

    @RequestMapping("/hello")
    R hello() {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        HelloVO hello_world = HelloVO.builder().message("hello world").messageList(list).build();
        return R.ok().data("hello_world",hello_world);
    }
}
