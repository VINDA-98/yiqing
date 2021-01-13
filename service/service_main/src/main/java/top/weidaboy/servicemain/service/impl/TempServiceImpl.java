package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.weidaboy.servicemain.entity.Temp;
import top.weidaboy.servicemain.mapper.TempMapper;
import top.weidaboy.servicemain.query.TempQuery;
import top.weidaboy.servicemain.service.StudentService;
import top.weidaboy.servicemain.service.TeacherService;
import top.weidaboy.servicemain.service.TempService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vinda
 * @since 2020-12-26
 */
@Service
public class TempServiceImpl extends ServiceImpl<TempMapper, Temp> implements TempService {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;


    @Override
    public void pageQuery(Page<Temp> pageParam, TempQuery tempQuery) {
        QueryWrapper<Temp> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified"); //按照时间降序排列
        if(tempQuery  == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }
        String name = tempQuery.getName();
        String classid = tempQuery.getClassid();
        String collegeid = tempQuery.getCollegeid();
        String majorid = tempQuery.getMajorid();
        String begin = tempQuery.getBegin();
        String end = tempQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
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
    public void pagcheck(Page<Temp> pageParam, TempQuery tempQuery) {
        QueryWrapper<Temp> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified"); //按照时间降序排列
        baseMapper.selectPage(pageParam,queryWrapper);
        String name = tempQuery.getName();
        String classid = tempQuery.getClassid();
        String collegeid = tempQuery.getCollegeid();
        String majorid = tempQuery.getMajorid();
        String begin = tempQuery.getBegin();
        String end = tempQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
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
    public IPage<TempQuery> getStudentNewTemp(IPage<TempQuery> page, TempQuery tempQuery) {
        return baseMapper.getStudentNewTemp(page,tempQuery);
    }

    @Override
    public IPage<TempQuery> getStudentAllTemp(IPage<TempQuery> page, TempQuery tempQuery) {
        return baseMapper.getStudentAllTemp(page,tempQuery);
    }

    @Override
    public IPage<TempQuery> getTeacherNewTemp(IPage<TempQuery> page, TempQuery tempQuery) {
        return baseMapper.getTeacherNewTemp(page,tempQuery);
    }

    @Override
    public IPage<TempQuery> getTeacherAllTemp(IPage<TempQuery> page, TempQuery tempQuery) {
        return baseMapper.getTeacherAllTemp(page,tempQuery);
    }

    @Override
    public void getUserTemp(Page<Temp> pageParam, String userid) {
        QueryWrapper<Temp> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userid)){
            queryWrapper.eq("userid",userid);
        }
        baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public List<TempQuery> getRefreshTemp() {
        return baseMapper.getRefreshTemp();
    }
}
