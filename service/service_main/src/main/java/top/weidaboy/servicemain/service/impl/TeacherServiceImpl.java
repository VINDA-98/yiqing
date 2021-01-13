package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import top.weidaboy.servicemain.entity.Teacher;
import top.weidaboy.servicemain.mapper.TeacherMapper;
import top.weidaboy.servicemain.query.TeacherQuery;
import top.weidaboy.servicemain.service.TeacherService;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Override
    public void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        //使用排序  升序 ：小到大
        queryWrapper.orderByAsc("id");
        if(teacherQuery == null){
            //Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }
        String name = teacherQuery.getName();  //姓名
        String collegeid = teacherQuery.getCollegeid(); //学院ID
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        String account = teacherQuery.getAccount();
        //如果名称栏不为空
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
        //大于或者等于当前时间
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        //小于或者等于当前时间
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public Teacher ReciveName(String name) {
        List<Teacher> teachers = baseMapper.selectList(null);
        PinYin pinYin = new PinYin();
        for (Teacher teacher : teachers) {
            //转大写
            //String s = convert.toUpperCase();
            //转小写
            String s =pinYin.StrToPinYin(teacher.getName());
            if(s.equals(name)) return teacher;
        }
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
    public boolean save(Teacher teacher) {
        NewID newID = new NewID();
        teacher.setId(newID.createdNewID());
        Integer result = baseMapper.insert(teacher);
        return null != result && result > 0;
    }
}

/*
    eq相等
    ne、neq不相等，
    gt大于，
    lt小于
    gte、ge大于等于
    lte、le 小于等于
    not非
    mod求模
    */
