package top.weidaboy.yiqing.dao;


import org.apache.ibatis.annotations.Param;
import top.weidaboy.yiqing.model.CityPO;
import top.weidaboy.yiqing.model.CityPOExample;

import java.util.List;

public interface CityDao {
    long countByExample(CityPOExample example);

    int deleteByExample(CityPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CityPO record);

    int insertSelective(CityPO record);

    List<CityPO> selectByExample(CityPOExample example);

    CityPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CityPO record, @Param("example") CityPOExample example);

    int updateByExample(@Param("record") CityPO record, @Param("example") CityPOExample example);

    int updateByPrimaryKeySelective(CityPO record);

    int updateByPrimaryKey(CityPO record);

    void truncate();
}
