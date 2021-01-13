package top.weidaboy.servicemain.controller;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.weidaboy.servicemain.config.FileUtil;
import top.weidaboy.servicemain.entity.UserExcel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/servicemain/Execl")
@CrossOrigin //跨域
@Api(description="表格控制类")
public class ExeclController {

    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response){
        List<UserExcel> list = new ArrayList<>();
        list.add(new UserExcel(1,"小刘",17));
        list.add(new UserExcel(2,"小周",11));
        list.add(new UserExcel(3,"小赵",15));
        list.add(new UserExcel(4,"小丽",44));
        //导出操作
        FileUtil.exportExcel(list,"通讯录","技术部",UserExcel.class,"通讯录.xls",response);
    }

    @RequestMapping("/import")
    public void importExcel(HttpServletRequest req){
        ImportParams params = new ImportParams();
        //解析excel，
        params.setHeadRows(1);
        params.setTitleRows(1);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)req;
        MultipartFile file = multipartRequest.getFile("upfile");
        List<UserExcel> list = FileUtil.importExcel(file, 1,1,UserExcel.class);
        System.out.println(list);
    }
}
