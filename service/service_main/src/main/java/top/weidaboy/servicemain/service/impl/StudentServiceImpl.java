package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import top.weidaboy.servicemain.entity.Student;
import top.weidaboy.servicemain.entity.Teacher;
import top.weidaboy.servicemain.entity.vo.StudentVO;
import top.weidaboy.servicemain.mapper.StudentMapper;
import top.weidaboy.servicemain.query.StudentQuery;
import top.weidaboy.servicemain.query.TeacherQuery;
import top.weidaboy.servicemain.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weidaboy.servicemain.util.NewID;
import top.weidaboy.servicemain.util.PinYin;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public void pageQuery(Page<Student> pageParam, StudentQuery studentQuery) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(studentQuery  == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }
        String name = studentQuery.getName();
        String account = studentQuery.getAccount();
        String classid = studentQuery.getClassid();
        String collegeid = studentQuery.getCollegeid();
        String majorid = studentQuery.getMajorid();
        String begin = studentQuery.getBegin();
        String end = studentQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(account)){
            queryWrapper.like("account",account);
        }
        //如果学院ID栏不为空
        if(!StringUtils.isEmpty(collegeid)){
            queryWrapper.eq("collegeid",collegeid);
        }
        //如果专业ID栏不为空
        if(!StringUtils.isEmpty(majorid)){
            queryWrapper.eq("majorid",majorid);
        }
        //如果班级ID栏不为空
        if(!StringUtils.isEmpty(classid)){
            queryWrapper.eq("classid",classid);
        }
        //大于或者等于当前时间
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        //小于或者等于当前时间
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public Student ReciveName(String name) {
        List<Student> students = baseMapper.selectList(null);
        PinYin pinYin = new PinYin();
        for (Student student : students) {
            //转大写
            //String s = convert.toUpperCase();
            //转小写
            String s = pinYin.StrToPinYin(student.getName());
            if(s.equals(name)) return student;
        }
        return null;
    }

    //封装student显示
    @Override
    public List<StudentVO> getStudentInfoByStudentID(String Id) {
        //根据id查询对应的学生
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        return null;
    }

    //重写删除方法
    @Override
    public boolean removeById(Serializable id) {
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    //重写添加方法
    @Override
    public boolean save(Student student) {
        NewID newID = new NewID();
        student.setId(newID.createdNewID());
        Integer result = baseMapper.insert(student);
        return null != result && result > 0;
    }
}
