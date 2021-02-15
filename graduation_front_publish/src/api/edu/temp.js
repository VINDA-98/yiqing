import request from '@/utils/request'
export default {
    //1 温度列表（条件查询分页）
    //current当前页 limit每页记录数 tempQuery条件对象
    getTempListPage(current,limit,tempQuery){
        return request({
            //url: '/eduservice/temp/'+current+"/"+limit,
            url: `/servicemain/temp/${current}/${limit}`,
            method: 'post',
            //tempQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: tempQuery
          })
    },
    getTempStudentListPage(current,limit,mode,tempQuery){
        return request({
            //url: '/eduservice/temp/'+current+"/"+limit,
            url: `/servicemain/temp/studentmode/${current}/${limit}/${mode}`,
            method: 'post',
            //tempQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: tempQuery
          })
    },
    getTempTeacherListPage(current,limit,mode,tempQuery){
        return request({
            //url: '/eduservice/temp/'+current+"/"+limit,
            url: `/servicemain/temp/teachermode/${current}/${limit}/${mode}`,
            method: 'post',
            //tempQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: tempQuery
          })
    },
   
    //添加温度
    addTemp(temp) {
        return request({
            url: `/servicemain/temp/save`,
            method: 'post',
            data: temp
            })
    },
    //删除温度
    deleteTempId(id) {
        return request({
            url: `/servicemain/temp/${id}`,
            method: 'delete'
          })
    },
    //修改温度
    updateTempInfo(temp) {
        return request({
            url: `/servicemain/temp/updatetemp`,
            method: 'post',
            data: temp
          })
    },
    //根据id查询温度
    getTempInfo(id) {
        return request({
            url: `/servicemain/temp/gettemp/${id}`,
            method: 'get'
            })
    },
    //获取所有得温度信息列表
    getTempList(){
        return request({
            url: `/servicemain/temp/getalltemp/`,
            method: 'get'
        })
    },
    //生成新的随机温度
    getRandomTemp(){
        return request({
            url: `/servicemain/temp/getRandomTemp/`,
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
            url: `/servicemain/temp/sendAsyncEmail/${id}`,
            method: 'post'
        })
    },
    //获得同个用户的所有数据
    getUserAllTemp(id,page,limit){
        return request({
            url: `/servicemain/temp/getuseralltemp/${page}/${limit}/${id}`,
            method: 'post',
        })
    },
    //查询各个学院更新温度信息的总人数
    getRefreshTemp(){
        return request({
            url: `/servicemain/temp/getRefreshTemp`,
            method: 'post',
        })
    },
    //发送更新提醒邮件
    sendRefreshEmail(id){
        return request({
            url: `/servicemain/temp/sendRefreshEmail/${id}`,
            method: 'post',
        })
    },
    //发送单片机指令内容
    sendMqtt(item,data){
        return request({
            url: `/servicemain/temp/send/${item}/${data}`,
            method: 'get',
        })
    },
}
