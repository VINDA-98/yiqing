import request from '@/utils/request'
export default {
    //1 学院列表（条件查询分页）
    //current当前页 limit每页记录数 collegeQuery条件对象
    getCollegeListPage(current,limit,collegeQuery){
        return request({
            //url: '/eduservice/college/'+current+"/"+limit,
            url: `/servicemain/college/${current}/${limit}`,
            method: 'post',
            //collegeQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: collegeQuery
          })
    },
    //添加学院
    addCollege(college) {
        return request({
            url: `/servicemain/college/save`,
            method: 'post',
            data: college
            })
    },
    //删除学院
    deleteCollegeId(id) {
        return request({
            url: `/servicemain/college/${id}`,
            method: 'delete'
          })
    },
    //修改学院
    updateCollegeInfo(college) {
        return request({
            url: `/servicemain/college/updatecollege`,
            method: 'post',
            data: college
          })
    },
    //根据id查询学院
    getCollegeInfo(id) {
        return request({
            url: `/servicemain/college/getcollege/${id}`,
            method: 'get'
            })
    },
    //获取所有得学院信息列表
    getCollegeList(){
        return request({
            url: `/servicemain/college/getallcollege/`,
            method: 'get'
        })
    },
    //获取人员
    getAllPeopel(){
        return request({
            url: `/servicemain/college/getallpeople/`,
            method: 'post'
        })
    },
}
