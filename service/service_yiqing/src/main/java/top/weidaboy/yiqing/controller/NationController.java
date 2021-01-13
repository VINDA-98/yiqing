package top.weidaboy.yiqing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weidaboy.commonutils.R;
import top.weidaboy.yiqing.model.NationVO;
import top.weidaboy.yiqing.service.NationService;

import javax.annotation.Resource;

@Api("国家疫情相关操作")
@Controller
@RequestMapping("/serviceyiqing/nations")
@Slf4j
@CrossOrigin //跨域
public class NationController {

    @Resource(name = "nationServiceImpl")
    NationService nationService;

    /**
     * 获取全国疫情数据
     *
     * @return
     */
    @ApiOperation("获取全国疫情数据")
    @GetMapping("/all")
    @ResponseBody
    R getNation() {
        log.info("收到请求：/nations/all");
        NationVO nationVo= nationService.select();
        return R.ok().data("nationVo",nationVo);
    }
}
