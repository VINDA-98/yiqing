package top.weidaboy.yiqing.service.impl;

import org.springframework.stereotype.Service;
import top.weidaboy.yiqing.dao.NationDao;
import top.weidaboy.yiqing.entity.Ncov;
import top.weidaboy.yiqing.model.NationVO;
import top.weidaboy.yiqing.service.NationService;
import top.weidaboy.yiqing.utils.NationMapper;

import javax.annotation.Resource;

@Service
public class NationServiceImpl implements NationService {

    @Resource
    private NationDao nationDao;


    @Override
    public void insert(Ncov.NewsList.Desc desc, Ncov.NewsList.Desc descFormer) {
        nationDao.insert(NationMapper.mapToPO(desc, descFormer));
    }

    @Override
    public NationVO select() {
        return NationMapper.mapToVO(nationDao.selectByPrimaryKey(1));
    }

    @Override
    public void truncate() {
        nationDao.truncate();
    }
}
