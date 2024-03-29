package top.weidaboy.yiqing.service.impl;


import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import top.weidaboy.yiqing.dao.ProvinceDao;
import top.weidaboy.yiqing.entity.NcovCity;
import top.weidaboy.yiqing.model.ProvinceMapVO;
import top.weidaboy.yiqing.model.ProvincePO;
import top.weidaboy.yiqing.model.ProvincePOExample;
import top.weidaboy.yiqing.model.ProvinceVO;
import top.weidaboy.yiqing.service.ProvinceService;
import top.weidaboy.yiqing.utils.ProvinceMapper;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    protected Mapper dozerMapper;

    @Resource
    private ProvinceDao provinceDao;

    @Override
    public int insertProvince(NcovCity.News province, LocalDate date) {
        provinceDao.insert(ProvinceMapper.mapToPO(province, date));
        return 1;
    }

    @Override
    public ProvinceMapVO getNationalProvince(LocalDate date, String type) {
        ProvincePOExample example = new ProvincePOExample();
        ProvincePOExample.Criteria criteria = example.createCriteria();
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        criteria.andDateEqualTo(Date.from(zonedDateTime.toInstant()));

        return ProvinceMapper.mapToNationVO(provinceDao.selectByExample(example), type);
    }

    @Override
    public Long countDate(LocalDate date) {
        ProvincePOExample example = new ProvincePOExample();
        ProvincePOExample.Criteria criteria = example.createCriteria();
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        criteria.andDateEqualTo(Date.from(zonedDateTime.toInstant()));
        return provinceDao.countByExample(example);
    }

    @Override
    public ProvinceVO selectByNameAndDate(String provinceShortName, LocalDate date) {
        ProvincePOExample example = new ProvincePOExample();
        ProvincePOExample.Criteria criteria = example.createCriteria();
        ZonedDateTime zonedDateTime = date.minusDays(1).atStartOfDay(ZoneId.systemDefault());

        // 查询今日和昨日数据
        criteria.andProvinceshortnameEqualTo(provinceShortName)
                .andDateGreaterThanOrEqualTo(Date.from(zonedDateTime.toInstant()));
        List<ProvincePO> pos = provinceDao.selectByExample(example);

        ProvincePO poToday = pos.get(0);
        ProvincePO poYesterday = pos.get(1);

        ProvinceVO vo = dozerMapper.map(poToday, ProvinceVO.class);

        vo.setConfirmedincr(poToday.getConfirmedcount() - poYesterday.getConfirmedcount());
        vo.setCurrentconfirmedincr(poToday.getCurrentconfirmedcount() - poYesterday.getCurrentconfirmedcount());
        vo.setCuredincr(poToday.getCuredcount() - poYesterday.getCuredcount());
        vo.setDeadincr(poToday.getDeadcount() - poYesterday.getDeadcount());

        return vo;
    }

    @Override
    public List<ProvincePO> selectByName(String provinceShortName) {
        LocalDate thisDayLastMon = LocalDate.now().minusDays(21);
        ZonedDateTime zonedDateTime = thisDayLastMon.atStartOfDay(ZoneId.systemDefault());

        ProvincePOExample example = new ProvincePOExample();
        ProvincePOExample.Criteria criteria = example.createCriteria();
        criteria.andProvinceshortnameEqualTo(provinceShortName)
                .andDateGreaterThanOrEqualTo(java.util.Date.from(zonedDateTime.toInstant()));
        example.setOrderByClause("date ASC");
        return provinceDao.selectByExample(example);
    }

    @Override
    public int update(NcovCity.News province, LocalDate date) {
        ProvincePOExample example = new ProvincePOExample();
        ProvincePOExample.Criteria criteria = example.createCriteria();
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        criteria.andProvinceshortnameEqualTo(province.getProvinceShortName())
                .andDateEqualTo(Date.from(zonedDateTime.toInstant()));
        ProvincePO po = ProvinceMapper.mapToPO(province, date);
        po.setId(provinceDao.selectByExample(example).get(0).getId());

        provinceDao.updateByExample(po, example);
        return 1;
    }

    @Override
    public Boolean exists(String provinceShortName, LocalDate date) {
        ProvincePOExample example = new ProvincePOExample();
        ProvincePOExample.Criteria criteria = example.createCriteria();
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        criteria.andDateEqualTo(Date.from(zonedDateTime.toInstant()))
                .andProvinceshortnameEqualTo(provinceShortName);

        return provinceDao.selectByExample(example).size() >= 1;
    }
}
