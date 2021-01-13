package top.weidaboy.yiqing.service;



import top.weidaboy.yiqing.entity.NcovCity;
import top.weidaboy.yiqing.model.ProvinceMapVO;
import top.weidaboy.yiqing.model.ProvincePO;
import top.weidaboy.yiqing.model.ProvinceVO;

import java.time.LocalDate;
import java.util.List;

public interface ProvinceService {

    int insertProvince(NcovCity.News province, LocalDate date);

    ProvinceMapVO getNationalProvince(LocalDate date, String type);

    Long countDate(LocalDate date);

    ProvinceVO selectByNameAndDate(String provinceShortName, LocalDate date);

    List<ProvincePO> selectByName(String provinceShortName);

    int update(NcovCity.News province, LocalDate date);

    Boolean exists(String provinceShortName, LocalDate date);
}
