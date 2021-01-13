package top.weidaboy.servicemain.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
@Api(description="游客控制类")
@RestController
@RequestMapping("/servicemain/tourist")
@CrossOrigin //跨域
public class TouristController {

}

