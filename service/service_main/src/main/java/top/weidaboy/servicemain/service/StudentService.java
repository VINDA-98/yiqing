package top.weidaboy.servicemain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.weidaboy.servicemain.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weidaboy.servicemain.entity.vo.StudentVO;
import top.weidaboy.servicemain.query.StudentQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
public interface StudentService extends IService<Student> {
    //添加查询学生业务
    void pageQuery(Page<Student> pageParam, StudentQuery studentQuery);
    //返回所有学生名字，并且对比
    Student ReciveName(String name);
    //返回学生显示信息
    List<StudentVO> getStudentInfoByStudentID(String Id);
}
