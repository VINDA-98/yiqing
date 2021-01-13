package top.weidaboy.yiqing.entity;



import top.weidaboy.yiqing.model.ProvincePO;
import top.weidaboy.yiqing.model.ProvinceTendencyVO;

import java.util.List;

public abstract class PathVar {
    public abstract List<ProvinceTendencyVO.Serie> getSeries();

    public abstract void insertData(ProvinceTendencyVO pTVO, ProvincePO po, ProvincePO poYesterday);
}
