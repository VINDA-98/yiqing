import request from '@/utils/request'
export default {
    //1 行程列表（条件查询分页）
    //current当前页 limit每页记录数 tripQuery条件对象
    getTripListPage(current,limit,tripQuery){
        return request({
            //url: '/eduservice/trip/'+current+"/"+limit,
            url: `/servicemain/trip/${current}/${limit}`,
            method: 'post',
            //tripQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: tripQuery
          })
    },
    getTripStudentListPage(current,limit,mode,tripQuery){
        return request({
            //url: '/eduservice/trip/'+current+"/"+limit,
            url: `/servicemain/trip/studentmode/${current}/${limit}/${mode}`,
            method: 'post',
            //tripQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: tripQuery
          })
    },
    getTripTeacherListPage(current,limit,mode,tripQuery){
        return request({
            //url: '/eduservice/trip/'+current+"/"+limit,
            url: `/servicemain/trip/teachermode/${current}/${limit}/${mode}`,
            method: 'post',
            //tripQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: tripQuery
          })
    },
   
    //添加行程
    addTrip(trip) {
        return request({
            url: `/servicemain/trip/save`,
            method: 'post',
            data: trip
            })
    },
    //删除行程
    deleteTripId(id) {
        return request({
            url: `/servicemain/trip/${id}`,
            method: 'delete'
          })
    },
    //修改行程
    updateTripInfo(trip) {
        return request({
            url: `/servicemain/trip/updatetrip`,
            method: 'post',
            data: trip
          })
    },
    //根据id查询行程
    getTripInfo(id) {
        return request({
            url: `/servicemain/trip/gettrip/${id}`,
            method: 'get'
            })
    },
    //获取所有得行程信息列表
    getTripList(){
        return request({
            url: `/servicemain/trip/getalltrip/`,
            method: 'get'
        })
    },
    //生成新的随机行程
    getRandomtrip(){
        return request({
            url: `/servicemain/trip/getRandomTrip/`,
            method: 'get'
        })
    },
    //开启设备监听
    openAMQP(){
        return request({
            url: `/servicemain/amqp/open`,
            method: 'get'
        })
    },
    //关闭设备监听
    closeAMQP(){
        return request({
            url: `/servicemain/amqp/close`,
            method: 'get'
        })
    },
    //发送邮件
    sendWarringEmail(id){
        return request({
            url: `/servicemain/trip/sendAsyncEmail/${id}`,
            method: 'post'
        })
    },
    //获得同个用户的所有数据
    getUserAlltrip(id,page,limit){
        return request({
            url: `/servicemain/trip/getuseralltrip/${page}/${limit}/${id}`,
            method: 'post',
        })
    },
    //查询各个学院更新行程信息的总人数
    getRefreshtrip(){
        return request({
            url: `/servicemain/trip/getRefreshTrip`,
            method: 'post',
        })
    },
    //发送更新提醒邮件
    sendRefreshEmail(id){
        return request({
            url: `/servicemain/trip/sendRefreshEmail/${id}`,
            method: 'post',
        })
    }

}
