package top.weidaboy.servicemain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.weidaboy.servicemain.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weidaboy.servicemain.query.TeacherQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
public interface TeacherService extends IService<Teacher> {
    //添加查询教师业务
    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);
    //返回所有教师名字，并且对比
    Teacher ReciveName(String name);


}
