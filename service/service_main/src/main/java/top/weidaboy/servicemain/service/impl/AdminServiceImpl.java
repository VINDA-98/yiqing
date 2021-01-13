package top.weidaboy.servicemain.service.impl;

import top.weidaboy.servicemain.entity.Admin;
import top.weidaboy.servicemain.mapper.AdminMapper;
import top.weidaboy.servicemain.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vinda
 * @since 2020-12-07
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
