package top.weidaboy.yiqing.dao;


import org.apache.ibatis.annotations.Param;
import top.weidaboy.yiqing.model.NationPO;
import top.weidaboy.yiqing.model.NationPOExample;

import java.util.List;

public interface NationDao {
    long countByExample(NationPOExample example);

    int deleteByExample(NationPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NationPO record);

    int insertSelective(NationPO record);

    List<NationPO> selectByExample(NationPOExample example);

    NationPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NationPO record, @Param("example") NationPOExample example);

    int updateByExample(@Param("record") NationPO record, @Param("example") NationPOExample example);

    int updateByPrimaryKeySelective(NationPO record);

    int updateByPrimaryKey(NationPO record);

    void truncate();
}
