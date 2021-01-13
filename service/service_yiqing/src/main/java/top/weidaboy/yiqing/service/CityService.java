package top.weidaboy.yiqing.service;


import top.weidaboy.yiqing.entity.NcovCity;
import top.weidaboy.yiqing.model.CityVO;

import java.time.LocalDate;
import java.util.List;

public interface CityService {
    int insertCity(NcovCity.News.City city, LocalDate date, String provinceName);

    Long countDate(LocalDate date);

    List<CityVO> selectCities(String province);

    void truncate();
}
