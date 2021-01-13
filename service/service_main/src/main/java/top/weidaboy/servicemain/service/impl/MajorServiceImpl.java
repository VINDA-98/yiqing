package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import top.weidaboy.servicemain.entity.Class;
import top.weidaboy.servicemain.entity.College;
import top.weidaboy.servicemain.entity.Major;
import top.weidaboy.servicemain.mapper.MajorMapper;
import top.weidaboy.servicemain.query.MajorQuery;
import top.weidaboy.servicemain.service.MajorService;
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
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Override
    public String majorNameByID(String majorid) {
        Major major = baseMapper.selectById(majorid);
        if(null!=major) return  major.getName();
        return null;
    }

    @Override
    public List<Major> getAllMajor() {
        List<Major> majors = baseMapper.selectList(null);
        return majors;
    }

    @Override
    public void pageQuery(Page<Major> pageParam, MajorQuery majorQuery) {
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(majorQuery  == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }
        String name = majorQuery.getName();
        String id = majorQuery.getId();
        String begin = majorQuery.getBegin();
        String end = majorQuery.getEnd();
        String collegeid = majorQuery.getCollegeid();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(id)){
            queryWrapper.like("id",id);
        }
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
        baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public List<Major> getMajorByCollegeid(String collegeid) {
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(collegeid)){
            queryWrapper.eq("collegeid",collegeid);
        }
        return baseMapper.selectList(queryWrapper);
    }
}
