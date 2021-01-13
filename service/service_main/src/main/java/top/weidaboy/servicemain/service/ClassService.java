package top.weidaboy.servicemain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.weidaboy.servicemain.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weidaboy.servicemain.entity.College;
import top.weidaboy.servicemain.entity.Major;
import top.weidaboy.servicemain.query.ClassQuery;
import top.weidaboy.servicemain.query.CollegeQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
public interface ClassService extends IService<Class> {
    //通过ID获取classname
    String classNameByID(String Studentid);
    //获取所有班级
    List<Class> getAllClass();
    //添加查询学院业务
    void pageQuery(Page<Class> pageParam, ClassQuery classQuery);

    //根据学院ID获取班级
    List<Class> getClassByCollegeid(String collegeid);

    //根据专业ID获取班级
    List<Class>  getClassByMajorID(String majorid);


}
