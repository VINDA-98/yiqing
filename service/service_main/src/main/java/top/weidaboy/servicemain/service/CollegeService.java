package top.weidaboy.servicemain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.weidaboy.servicemain.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weidaboy.servicemain.entity.Student;
import top.weidaboy.servicemain.query.CollegeQuery;
import top.weidaboy.servicemain.query.StudentQuery;
import top.weidaboy.servicemain.util.NewID;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
public interface CollegeService extends IService<College> {
    //通过学院ID获取专业名称
    public String collegeNameByID(String collegeid);
    //获取所有的college
    List<College> getAllCollege();

    //添加查询学院业务
    void pageQuery(Page<College> pageParam, CollegeQuery collegeQuery);

    //获取人数列表
    List<CollegeQuery> getAllPeopel ();

}
