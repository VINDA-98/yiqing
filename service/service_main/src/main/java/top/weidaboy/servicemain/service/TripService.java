package top.weidaboy.servicemain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.weidaboy.servicemain.entity.Trip;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weidaboy.servicemain.query.TripQuery;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vinda
 * @since 2020-12-28
 */
public interface TripService extends IService<Trip> {

    //获得随机行程
    public String getRomdomAddress();

    public void pageQuery(Page<Trip> pageParam, Trip tripQuery);

    public void pagcheck(Page<Trip> pageParam, Trip tripQuery);

    //查询学生
    public  IPage<TripQuery> getStudentNewTrip(IPage<TripQuery> pageParam, @Param("tq")TripQuery tripQuery);

    public IPage<TripQuery> getStudentAllTrip(IPage<TripQuery> pageParam, @Param("tq")TripQuery tripQuery);

    //查询教师
    public IPage<TripQuery>  getTeacherNewTrip(IPage<TripQuery> pageParam, @Param("tq")TripQuery tripQuery);

    public IPage<TripQuery>  getTeacherAllTrip(IPage<TripQuery> pageParam, @Param("tq")TripQuery tripQuery);

    //查询对应的用户ID所有信息
    public void getUserTrip(Page<Trip> pageParam, String userid);

    //查询各个学院更新温度信息的总人数
    List<TripQuery> getRefreshTrip();

}
