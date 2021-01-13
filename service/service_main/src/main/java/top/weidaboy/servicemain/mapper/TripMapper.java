package top.weidaboy.servicemain.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.weidaboy.servicemain.entity.Trip;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.weidaboy.servicemain.query.TempQuery;
import top.weidaboy.servicemain.query.TripQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vinda
 * @since 2020-12-28
 */
public interface TripMapper extends BaseMapper<Trip> {
    //查询学生
    public IPage<TripQuery> getStudentNewTrip(IPage<TripQuery> pageParam, @Param("tq") TripQuery tripQuery);

    public IPage<TripQuery> getStudentAllTrip(IPage<TripQuery> pageParam, @Param("tq")TripQuery tripQuery);

    //查询教师
    public IPage<TripQuery> getTeacherNewTrip(IPage<TripQuery> pageParam, @Param("tq")TripQuery tripQuery);

    public IPage<TripQuery> getTeacherAllTrip(IPage<TripQuery> pageParam, @Param("tq")TripQuery tripQuery);

    //查询各个学院更新温度信息的总人数
    List<TripQuery> getRefreshTrip ();
}
