package top.weidaboy.servicemain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import top.weidaboy.servicemain.entity.Trip;
import top.weidaboy.servicemain.mapper.TripMapper;
import top.weidaboy.servicemain.query.TripQuery;
import top.weidaboy.servicemain.service.TripService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weidaboy.servicemain.util.LocalUtil;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vinda
 * @since 2020-12-28
 */
@Service
public class TripServiceImpl extends ServiceImpl<TripMapper, Trip> implements TripService {

    Random r=new Random();//设置随机种子
    LocalUtil lu =  LocalUtil.getInstance(); //获取实例

    //获得随机行程
    @Override
    public String getRomdomAddress() {

            String trip = "";
            String city = "";
            String town = "";
            String smallTown = "";//乡镇
            List<String> Provinces = lu.getProvinces("中国"); //获得所有省份
            //省份随机
            int i=r.nextInt(33);    //生成[0,33]区间的整数
            List<String> cities = lu.getCities("中国",Provinces.get(i)); //获得省份中所有的城市
            city = Provinces.get(i);  //获得省份
            if(city.equals("北京")  || city .equals("天津") || city.equals("上海") || city.equals("重庆") ){
                i = r.nextInt(cities.size());
                town = cities.get(i);     //获得城市
                trip = city+"市/市辖区/"+town+"/";
                System.out.println("trip:"+trip);
                return trip;
            }
            i = r.nextInt(cities.size());
            town = cities.get(i);     //获得城市

             System.out.println("city:"+city);
             System.out.println("town:"+town);
            List<String> xiancheng = lu.xiancheng("中国", city,town); //获得城市下所有县城
            for (String s : xiancheng) {
                System.out.print(s+ " ");
            }
            System.out.println();
            //java.lang.IllegalArgumentException: bound must be positive “非法参数异常：约束必须为正
            if(xiancheng.size() !=0 ){
                i = r.nextInt(xiancheng.size());
                smallTown = xiancheng.get(i); //获得乡镇
            }
            System.out.println("smallTown:"+smallTown);


            //从JDK5开始，Java编译器就做了优化，使用“+”拼接字符串，编译器编译后实际就自动优化为使用StringBuilder。
            //中国共计34个省级行政区，包括23个省、5个自治区、4个直辖市、2个特别行政区。
            // 中国 广西 河池 东兰
            if(city.equals("内蒙古")|| city.equals("西藏")){
                trip = city+"自治区/";
            }
            else if(city.equals("广西") ){
                trip = city+"壮族自治区/";
            }
            else if(city.equals("宁夏")  ){
                trip = city+"回族自治区/";
            }
            else if(city.equals("新疆")  ){
                trip = city+"维吾尔自治区/";
            }
            else if(city.equals("新疆") ){
                trip = city+"维吾尔自治区/";
            }
            else if(city.equals("香港") ||city.equals("澳门")){
                trip = city+"特别行政区/";
            }
            else {
                trip  = city+"省/";
            }

            if(!smallTown.equals("")) trip += town+"/"+smallTown+"/";
            else trip  += town+"/";
            System.out.println("trip:"+trip);
            return trip;
        }

    @Override
    public void pageQuery(Page<Trip> pageParam, Trip tripQuery) {

    }

    @Override
    public void pagcheck(Page<Trip> pageParam, Trip tripQuery) {

    }

    @Override
    public IPage<TripQuery> getStudentNewTrip(IPage<TripQuery> pageParam, @Param("tq") TripQuery tripQuery) {
        return baseMapper.getStudentNewTrip(pageParam,tripQuery);
    }

    @Override
    public IPage<TripQuery> getStudentAllTrip(IPage<TripQuery> pageParam, @Param("tq") TripQuery tripQuery) {
        return baseMapper.getStudentAllTrip(pageParam,tripQuery);
    }

    @Override
    public IPage<TripQuery> getTeacherNewTrip(IPage<TripQuery> pageParam,@Param("tq")  TripQuery tripQuery) {
        return baseMapper.getTeacherNewTrip(pageParam,tripQuery);
    }

    @Override
    public IPage<TripQuery> getTeacherAllTrip(IPage<TripQuery> pageParam, @Param("tq")  TripQuery tripQuery) {
        return baseMapper.getTeacherAllTrip(pageParam,tripQuery);
    }


//        QueryWrapper<Trip> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("gmt_tripend"); //根据最新时间排序
//        queryWrapper.groupBy("name"); //用名称分组
//        if(tripQuery  == null){
//            baseMapper.selectPage(pageParam,queryWrapper);
//            return;
//        }
//        String name    = tripQuery.getName();
//        String classid = tripQuery.getClassid();
//        String collegeid = tripQuery.getCollegeid();
//        String majorid = tripQuery.getMajorid();
//        String begin = tripQuery.getBegin();
//        String end = tripQuery.getEnd();
//        if(!StringUtils.isEmpty(name)){
//            queryWrapper.like("name",name);
//        }
//        //如果学院ID栏不为空
//        if(!StringUtils.isEmpty(collegeid)){
//            queryWrapper.eq("collegeid",collegeid);
//        }
//        //如果专业ID栏不为空
//        if(!StringUtils.isEmpty(majorid)){
//            queryWrapper.eq("majorid",majorid);
//        }
//        //如果班级ID栏不为空
//        if(!StringUtils.isEmpty(classid)){
//            queryWrapper.eq("classid",classid);
//        }
//        //大于或者等于当前时间
//        if (!StringUtils.isEmpty(begin)) {
//            queryWrapper.ge("gmt_tripend", begin);
//        }
//        //小于或者等于当前时间
//        if (!StringUtils.isEmpty(end)) {
//            queryWrapper.le("gmt_tripend", end);
//        }
//        baseMapper.selectPage(pageParam, queryWrapper);




    @Override
    public void getUserTrip(Page<Trip> pageParam, String userid) {

    }

    @Override
    public List<TripQuery> getRefreshTrip() {
        return baseMapper.getRefreshTrip();
    }
}
