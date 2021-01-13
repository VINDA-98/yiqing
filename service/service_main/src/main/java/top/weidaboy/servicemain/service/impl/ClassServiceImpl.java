package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import top.weidaboy.servicemain.entity.Class;
import top.weidaboy.servicemain.mapper.ClassMapper;
import top.weidaboy.servicemain.query.ClassQuery;
import top.weidaboy.servicemain.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
    @Override
    public String classNameByID(String classid) {
        Class aClass = baseMapper.selectById(classid);
        if(null!=aClass) return  aClass.getName();
        return null;
    }

    @Override
    public List<Class> getAllClass() {
        List<Class> myclass = baseMapper.selectList(null);
        return myclass;
    }


    @Override
    public  void pageQuery(Page<Class> pageParam, ClassQuery classQuery){
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(classQuery  == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }
        String name = classQuery.getName();
        String id = classQuery.getId();
        String begin = classQuery.getBegin();
        String end = classQuery.getEnd();
        String collegeid = classQuery.getCollegeid();
        String majorid = classQuery.getMajorid();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(id)){
            queryWrapper.like("id",id);
        }
        if(!StringUtils.isEmpty(collegeid)){
            queryWrapper.eq("collegeid",collegeid);
        }
        if(!StringUtils.isEmpty(majorid)){
            queryWrapper.eq("majorid",majorid);
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
    public List<Class> getClassByCollegeid(String collegeid) {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(collegeid)){
            queryWrapper.eq("collegeid",collegeid);
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Class> getClassByMajorID(String majorid) {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(majorid)){
            queryWrapper.eq("majorid",majorid);
        }
        return baseMapper.selectList(queryWrapper);
    }
}
