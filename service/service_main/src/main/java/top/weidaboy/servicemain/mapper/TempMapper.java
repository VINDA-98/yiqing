package top.weidaboy.servicemain.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import top.weidaboy.servicemain.entity.Temp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.weidaboy.servicemain.query.TempQuery;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author vinda
 * @since 2020-12-26
 */
public interface TempMapper extends BaseMapper<Temp> {
    //查询学生
    IPage<TempQuery> getStudentNewTemp(IPage<TempQuery> page, @Param("tq") TempQuery tempQuery);

    IPage<TempQuery> getStudentAllTemp(IPage<TempQuery> page, @Param("tq") TempQuery tempQuery);

    //查询教师
    IPage<TempQuery> getTeacherNewTemp(IPage<TempQuery> page, @Param("tq") TempQuery tempQuery);

    IPage<TempQuery> getTeacherAllTemp(IPage<TempQuery> page, @Param("tq") TempQuery tempQuery);

    //查询各个学院更新温度信息的总人数
    List<TempQuery> getRefreshTemp ();
}
