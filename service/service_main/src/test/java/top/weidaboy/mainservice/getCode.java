package top.weidaboy.mainservice;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import top.weidaboy.servicemain.util.LocalUtil;

import java.util.List;
import java.util.Random;

public class getCode {

    @Test
    public void main1() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("vinda");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        /*
         * mp生成service层代码，默认接口名称第一个字母有 I
         * UcenterService
         * */
        gc.setServiceName("%sService");	//去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/bishe?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("weida");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("servicemain"); //模块名
        pc.setParent("top.weidaboy");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("trip");  //对应的数据库名称
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        //strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }

    @Test
    public void test01(){
        Random r=new Random();//设置随机种子
        String trip = "";
        String city = "";
        String town = "";
        String smallTown = "";//乡镇

        LocalUtil lu =  LocalUtil.getInstance(); //获取实例
        List<String> Provinces = lu.getProvinces("中国"); //获得所有省份
        //省份随机
        int i=r.nextInt(33);    //生成[0,33]区间的整数
        List<String> cities = lu.getCities("中国",Provinces.get(i)); //获得省份中所有的城市
        city = Provinces.get(i);  //获得省份

        i = r.nextInt(cities.size());
        town = cities.get(i);     //获得城市

        // 中国 广西 河池 东兰
        List<String> xiancheng = lu.xiancheng("中国", city,town); //获得城市下所有县城
        i = r.nextInt(xiancheng.size());

        smallTown = xiancheng.get(i); //获得乡镇

        System.out.println(city+" "+ town+" "+" "+smallTown);
        //从JDK5开始，Java编译器就做了优化，使用“+”拼接字符串，编译器编译后实际就自动优化为使用StringBuilder。
        //中国共计34个省级行政区，包括23个省、5个自治区、4个直辖市、2个特别行政区。
        if(city == "内蒙古" || city =="西藏"){
            trip = city+"自治区/";
        }
        if(city == "广西" ){
            trip = city+"壮族自治区/";
        }
        if(city == "宁夏" ){
            trip = city+"回族自治区/";
        }
        if(city == "新疆" ){
            trip = city+"维吾尔自治区/";
        }
        if(city == "新疆" ){
            trip = city+"维吾尔自治区/";
        }
        if(city == "北京" ||city == "天津"||city == "上海"||city == "重庆"){
            trip = city+"市/";
        }
        if(city == "香港" ||city == "澳门"){
            trip = city+"特别行政区/";
        }

        trip =city+"省/"+town+"/"+smallTown+"/";
        System.out.println(trip);//注意市前面的就ok
    }

    @Test
    public  void test02(){
        LocalUtil lu =  LocalUtil.getInstance(); //获取实例
        List<String> Provinces = lu.xiancheng("中国","北京","456"); //获得所有省份
    }
}
