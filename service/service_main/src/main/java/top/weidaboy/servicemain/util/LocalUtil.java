package top.weidaboy.servicemain.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LocalUtil {
    //各地区xml文件路径
    private static final String LOCAL_LIST_PATH
            = "D:\\ADaDemo\\graduation\\service\\service_main\\src\\main\\java\\top\\weidaboy\\servicemain\\mapper\\xml\\LocList.xml";

    //所有国家名称List
    private static final List<String> COUNTRY_REGION = new ArrayList<String>();
    private static LocalUtil localutil;
    private SAXReader reader;
    private Document document;

    //根元素
    private Element rootElement;

    //初始化
    private LocalUtil(){
        //1.读取
        reader = new SAXReader();
        try {
            document = reader.read(LOCAL_LIST_PATH);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //2.获得根元素
        rootElement =  document.getRootElement();

        //3.初始化所有国家名称列表
        Iterator it =  rootElement.elementIterator();

        Element ele = null;
        while(it.hasNext()){
            ele = (Element)it.next();
            COUNTRY_REGION.add(ele.attributeValue("Name"));
        }
    }

    /**
     *
     * @author		Vinda
     * @TODO		功能：	获取所有国家名称
     * @return		String[]
     */
    public List<String> getCountry(){
        return COUNTRY_REGION;
    }

    /**
     *
     * @author		Vinda
     * @TODO		功能：	根据国家名获取该国所有省份
     * @param countryName	国家名，从getCountry()从取出
     * @return		List<Element>
     */
    private List<Element> provinces(String countryName){
        Iterator it =  rootElement.elementIterator();
        List<Element> provinces = new ArrayList<Element>();
        Element ele = null;
        while(it.hasNext()){
            ele = (Element)it.next();
            COUNTRY_REGION.add(ele.attributeValue("Name"));
            if(ele.attributeValue("Name").equals(countryName)){
                provinces = ele.elements();
                break;
            }
        }
        return provinces;
    }

    /**
     *
     * @author		Vinda
     * @TODO		功能：	根据国家名获取该国所有省份
     * @time		2016-8-26 上午9:07:21
     * @param countryName	国家名，从getCountry()从取出
     * @return		List<Element>
     */
    public List<String> getProvinces(String countryName){
        List<Element> tmp = this.provinces(countryName);
        List<String> list = new ArrayList<String>();
        for(int i=0; i<tmp.size(); i++){
            list.add(tmp.get(i).attributeValue("Name"));
        }
        return list;
    }

    /**
     *
     * @author		Vinda
     * @TODO		功能：根据国家名和省份名，获取该省城市名列表
     * @param           provinceName
     * @return
     */
    private List<Element> cities(String countryName, String provinceName){
        List<Element> provinces =  this.provinces(countryName);
        List<Element> cities = new ArrayList<Element>();
        if(provinces==null || provinces.size()==0){		//没有这个城市
            return cities;
        }

        for(int i=0; i<provinces.size(); i++){
            if(provinces.get(i).attributeValue("Name").equals(provinceName)){
                cities = provinces.get(i).elements();
                break;
            }
        }
        return cities;
    }



    //获取县城
    public List<String> xiancheng(String countryName, String provinceName,String townName ){
        List<Element> provinces =  this.provinces(countryName);
        List<Element> cities = new ArrayList<>();               //省份元素
        List<Element> town = new ArrayList<>();                 //县城元素
        List<String> citiesstr = new ArrayList<>();             //获取省份中的各个城市
        if(provinces==null || provinces.size()==0){		        //没有这个城市
            return citiesstr;
        }

        //获取对应省份 23个省、5个自治区、4个直辖市、2个特别行政区
        for(int i=0; i<provinces.size(); i++){
            if(provinces.get(i).attributeValue("Name").equals(provinceName)){
                cities = provinces.get(i).elements();
                break;
            }
        }

        //获取对应城市
        for(int i=0; i<cities.size(); i++){
            if(cities.get(i).attributeValue("Name").equals(townName)){
                town = cities.get(i).elements();
                break;
            }
        }

        //将整个省份元素给到tmp
        List<Element> tmp =  town;
        for(int i=0; i<tmp.size(); i++){
            citiesstr.add(tmp.get(i).attributeValue("Name")); //添加到str数组
        }
        return citiesstr;
    }



    /**
     *
     * @author		LiuJinan
     * @TODO		功能：根据国家名和省份名获取城市列表
     * @time		2016-8-26 下午4:55:55
     * @param countryName
     * @param provinceName
     * @return		List<String>
     */
    public List<String> getCities(String countryName, String provinceName){
        List<Element> tmp =  this.cities(countryName, provinceName);
        List<String> cities = new ArrayList<String>();
        for(int i=0; i<tmp.size(); i++){
            cities.add(tmp.get(i).attributeValue("Name"));
        }
        return cities;
    }

    public static LocalUtil getInstance(){
        if(localutil==null){
            localutil = new LocalUtil();
        }
        return localutil;
    }


}
