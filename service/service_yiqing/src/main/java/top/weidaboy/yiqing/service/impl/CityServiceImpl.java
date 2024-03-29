package top.weidaboy.yiqing.service.impl;

import org.springframework.stereotype.Service;
import top.weidaboy.yiqing.dao.CityDao;
import top.weidaboy.yiqing.entity.NcovCity;
import top.weidaboy.yiqing.model.CityPOExample;
import top.weidaboy.yiqing.model.CityVO;
import top.weidaboy.yiqing.service.CityService;
import top.weidaboy.yiqing.utils.CityMapper;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityDao cityDao;


    @Override
    public int insertCity(NcovCity.News.City city, LocalDate date, String provinceName) {
        cityDao.insert(CityMapper.mapToPO(city, date, provinceName));
        return 1;
    }

    @Override
    public Long countDate(LocalDate date) {
        CityPOExample example = new CityPOExample();
        CityPOExample.Criteria criteria = example.createCriteria();
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        criteria.andDateEqualTo(Date.from(zonedDateTime.toInstant()));
        return cityDao.countByExample(example);
    }

    @Override
    public List<CityVO> selectCities(String province) {
        LocalDate date = LocalDate.now().minusDays(1);
        ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        CityPOExample example = new CityPOExample();
        CityPOExample.Criteria criteria = example.createCriteria();
        criteria.andProvinceshortnameEqualTo(province);
        return CityMapper.mapToListVO(cityDao.selectByExample(example));
    }

    @Override
    public void truncate() {
        cityDao.truncate();
    }
}
