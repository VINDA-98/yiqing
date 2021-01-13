package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import top.weidaboy.servicemain.entity.College;
import top.weidaboy.servicemain.entity.Major;
import top.weidaboy.servicemain.entity.Student;
import top.weidaboy.servicemain.mapper.CollegeMapper;
import top.weidaboy.servicemain.query.CollegeQuery;
import top.weidaboy.servicemain.service.CollegeService;
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
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Override
    public String collegeNameByID(String collegeid) {
        College college = baseMapper.selectById(collegeid);
        if(null!=college) return  college.getName();
        return null;
    }

    @Override
    public List<College> getAllCollege() {
        List<College> colleges = baseMapper.selectList(null);
        return colleges;
    }

    @Override
    public void pageQuery(Page<College> pageParam, CollegeQuery collegeQuery) {
        QueryWrapper<College> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(collegeQuery  == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }
        String name = collegeQuery.getName();
        String id = collegeQuery.getId();
        String begin = collegeQuery.getBegin();
        String end = collegeQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(id)){
            queryWrapper.like("id",id);
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
    public List<CollegeQuery> getAllPeopel() {
        return baseMapper.getAllPeopel();
    }

    //重写添加方法
    public boolean save(College college) {
        Integer result = baseMapper.insert(college);
        return null != result && result > 0;
    }
}
