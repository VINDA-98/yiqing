package top.weidaboy.servicemain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.weidaboy.servicemain.entity.College;
import top.weidaboy.servicemain.entity.Major;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weidaboy.servicemain.query.MajorQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
public interface MajorService extends IService<Major> {
    //通过专业ID获取专业名称
    String majorNameByID(String majorid);

    //获取所有的专业
    List<Major> getAllMajor();

    void pageQuery(Page<Major> pageParam, MajorQuery majorQuery);

    //根据学院ID获取专业
    List<Major>  getMajorByCollegeid(String collegeid);

}
