package top.weidaboy.servicemain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.weidaboy.servicemain.entity.Temp;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weidaboy.servicemain.query.TempQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vinda
 * @since 2020-12-26
 */
public interface TempService extends IService<Temp> {
    public void pageQuery(Page<Temp> pageParam, TempQuery tempQuery) ;
    public void pagcheck(Page<Temp> pageParam, TempQuery tempQuery);

    //查询学生
    IPage<TempQuery> getStudentNewTemp(IPage<TempQuery> page , @Param("tq") TempQuery tempQuery);
    IPage<TempQuery> getStudentAllTemp(IPage<TempQuery> page , @Param("tq") TempQuery tempQuery);

    //查询教师
    IPage<TempQuery> getTeacherNewTemp(IPage<TempQuery> page , @Param("tq") TempQuery tempQuery);
    IPage<TempQuery> getTeacherAllTemp(IPage<TempQuery> page , @Param("tq") TempQuery tempQuery);

    //查询对应的用户ID所有信息
    public void getUserTemp(Page<Temp> pageParam,String userid);

    //查询各个学院更新温度信息的总人数
    List<TempQuery> getRefreshTemp();
}
