package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.weidaboy.servicemain.entity.Temp;
import top.weidaboy.servicemain.entity.Tourist;
import top.weidaboy.servicemain.entity.Trip;
import top.weidaboy.servicemain.mapper.TouristMapper;
import top.weidaboy.servicemain.service.TouristService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
@Service
public class TouristServiceImpl extends ServiceImpl<TouristMapper, Tourist> implements TouristService {


}
