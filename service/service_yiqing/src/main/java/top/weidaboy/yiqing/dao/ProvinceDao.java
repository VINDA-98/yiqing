package top.weidaboy.yiqing.dao;

import org.apache.ibatis.annotations.Param;
import top.weidaboy.yiqing.model.ProvincePO;
import top.weidaboy.yiqing.model.ProvincePOExample;

import java.util.List;

public interface ProvinceDao {
    long countByExample(ProvincePOExample example);

    int deleteByExample(ProvincePOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProvincePO record);

    int insertSelective(ProvincePO record);

    List<ProvincePO> selectByExample(ProvincePOExample example);

    ProvincePO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProvincePO record, @Param("example") ProvincePOExample example);

    int updateByExample(@Param("record") ProvincePO record, @Param("example") ProvincePOExample example);

    int updateByPrimaryKeySelective(ProvincePO record);

    int updateByPrimaryKey(ProvincePO record);
}