package top.weidaboy.yiqing.entity;



import top.weidaboy.yiqing.model.ProvincePO;
import top.weidaboy.yiqing.model.ProvinceTendencyVO;

import java.util.ArrayList;
import java.util.List;

public class ConfirmedPathVar extends PathVar {
    @Override
    public List<ProvinceTendencyVO.Serie> getSeries() {
        List<ProvinceTendencyVO.Serie> series = new ArrayList<>();
        series.add(ProvinceTendencyVO.Serie.builder()
                .name("累计确诊")
                .data(new ArrayList<>())
                .build());
        return series;
    }

    @Override
    public void insertData(ProvinceTendencyVO pTVO, ProvincePO po, ProvincePO poYesterday) {
        pTVO.getSeries().get(0).getData().add(po.getConfirmedcount());
    }

}
