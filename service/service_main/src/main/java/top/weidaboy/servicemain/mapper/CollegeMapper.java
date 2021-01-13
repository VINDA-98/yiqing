package top.weidaboy.servicemain.mapper;

import top.weidaboy.servicemain.entity.College;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.weidaboy.servicemain.query.CollegeQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
public interface CollegeMapper extends BaseMapper<College> {
    //获取人数列表
    List <CollegeQuery>  getAllPeopel();
}
