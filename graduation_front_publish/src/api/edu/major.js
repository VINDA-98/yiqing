import request from '@/utils/request'
export default {
    //1 专业列表（条件查询分页）
    //current当前页 limit每页记录数 majorQuery条件对象
    getMajorListPage(current,limit,majorQuery){
        return request({
            //url: '/eduservice/major/'+current+"/"+limit,
            url: `/servicemain/major/${current}/${limit}`,
            method: 'post',
            //majorQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: majorQuery
          })
    },
    //添加专业
    addMajor(major) {
        return request({
            url: `/servicemain/major/save`,
            method: 'post',
            data: major
            })
    },
    //删除专业
    deleteMajorId(id) {
        return request({
            url: `/servicemain/major/${id}`,
            method: 'delete'
          })
    },
    //修改专业
    updateMajorInfo(major) {
        return request({
            url: `/servicemain/major/updatemajor`,
            method: 'post',
            data: major
          })
    },
    //根据id查询专业
    getMajorInfo(id) {
        return request({
            url: `/servicemain/major/getmajor/${id}`,
            method: 'get'
            })
    },
    //根据学院ID查询专业
    
    getMajorInfoByCollegeID(id) {
        return request({
            url: `/servicemain/major/getmajorbycollegeid/${id}`,
            method: 'get'
            })
    },
    //根据专业ID查询学院
    getByCollegeByMajorID(id) {
        return request({
            url: `/servicemain/major/getbycollegebymajorid/${id}`,
            method: 'get'
            })
    },
    //获取所有得专业信息列表
    getMajorList(){
    return request({
        url: `/servicemain/major/getallmajor/`,
        method: 'get'
    })
}
}
