import request from '@/utils/request'
export default {
    //获取全国数据
    getNation(){
        return request({
            url: `/serviceyiqing/nations/all`,
            method: 'get',
        })
    },
    //获取各个省市的确诊
    getNationalProvincesCurrentconfirmed(){
        return request({
            url: `/serviceyiqing/statistics/provinces/currentconfirmed`,
            method: 'get',
        })
    },
    getNationalProvinces(type){
        return request({
            url: `serviceyiqing/statistics/provinces/${type}`,
            method: 'get',
        })
    },
    //获取某个省的数据
    getProvince(province,type){
        return request({
            url: `/serviceyiqing/statistics/provinces/one/${province}`,
            method: 'get',
        })
    },
    //获取某省疫情趋势
    getTendency(province){
        return request({
            url: `/serviceyiqing/statistics/provinces/one/tends/${province}/${type}`,
            method: 'get',
        })
    },
    //获取某省的所有城市疫情
    getCities(province){
        return request({
            url: `/serviceyiqing/cities/list/${province}`,
            method: 'get',
        })
    },
}
