package top.weidaboy.yiqing.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.weidaboy.commonutils.R;
import top.weidaboy.yiqing.model.ProvinceMapVO;
import top.weidaboy.yiqing.model.ProvinceTendencyVO;
import top.weidaboy.yiqing.model.ProvinceVO;
import top.weidaboy.yiqing.service.ProvinceService;
import top.weidaboy.yiqing.utils.ProvinceMapper;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;

@Api("省级疫情相关操作")
@Controller
@RequestMapping("/serviceyiqing/statistics")
@CrossOrigin //跨域
@Slf4j
public class ProvinceController {

    @Resource(name = "provinceServiceImpl")
    ProvinceService provinceService;

    @ApiOperation("获取全国各省疫情数据")
    @GetMapping("/provinces/{type}")
    //confirmed ：所有的  currentconfirmed：当日的
    @ResponseBody
    R getNationalProvinces(@PathVariable String type) {
        log.info("收到请求：/statistics/provinces/" + type);
        ProvinceMapVO  provinceMapVO= provinceService.getNationalProvince(LocalDate.now(), type);
        return R.ok().data("provinceMapVO",provinceMapVO);
    }

    /**
     * 获取某个省的数据
     *
     * @param province URL编码后的省名称
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation("获取某个省的数据")
    @GetMapping("/provinces/one/{province}")
    @ResponseBody
    R getProvince(@PathVariable String province) throws UnsupportedEncodingException {
        String decodedName = URLDecoder.decode(URLDecoder.decode(province, "UTF-8"), "UTF-8");
        log.info("收到请求：/statistics/one/" + decodedName);
        ProvinceVO provinceVO = provinceService.selectByNameAndDate(decodedName, LocalDate.now());
        return R.ok().data("provinceVO",provinceVO);
    }

    /**
     * 获取某省疫情趋势
     *
     * @param province URL编码后的省名称
     * @param type     数据类型
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation("获取某省疫情趋势")
    @GetMapping("/provinces/one/tends/{province}/{type}")
    @ResponseBody
    R getTendency(@PathVariable String province, @PathVariable String type)
            throws UnsupportedEncodingException {
        String decodedName = URLDecoder.decode(URLDecoder.decode(province, "UTF-8"), "UTF-8");
        log.info("收到请求：/statistics/one/tends/" + decodedName + "/" + type);
        ProvinceTendencyVO provinceTendencyVO = ProvinceMapper.mapToTendency(provinceService.selectByName(decodedName), type);
        return R.ok().data("provinceTendencyVO",provinceTendencyVO);
    }
}
