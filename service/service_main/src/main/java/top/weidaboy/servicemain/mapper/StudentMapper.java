package top.weidaboy.servicemain.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.weidaboy.servicemain.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
public interface StudentMapper extends BaseMapper<Student> {
        IPage<Map> studentInfo(Page page , Student student);
}
