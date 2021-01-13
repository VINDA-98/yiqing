package top.weidaboy.yiqing.service;


import top.weidaboy.yiqing.entity.Ncov;
import top.weidaboy.yiqing.model.NationVO;

public interface NationService {
    void insert(Ncov.NewsList.Desc desc, Ncov.NewsList.Desc descFormer);

    NationVO select();

    void truncate();
}
