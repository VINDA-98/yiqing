package top.weidaboy.yiqing.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.yiqing.model.CityVO;
import top.weidaboy.yiqing.service.CityService;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Api("市级疫情相关操作")
@Controller
@RequestMapping("/serviceyiqing/cities")
@Slf4j
@CrossOrigin //跨域
public class CityController {

    @Resource(name = "cityServiceImpl")
    CityService cityService;

    /**
     * 获取某省的所有城市疫情
     * @param province URL编码后的省名称
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation("获取某省的所有城市疫情")
    @GetMapping("list/{province}")
    @ResponseBody
    public R getCities(@PathVariable String province) throws UnsupportedEncodingException {
        String decodedName = URLDecoder.decode(URLDecoder.decode(province, "UTF-8"), "UTF-8");
        log.info("收到请求：/citieslist/" + decodedName);
        List<CityVO> cityVOS = cityService.selectCities(decodedName);
        return R.ok().data("cityVOS",cityVOS);
    }
}
