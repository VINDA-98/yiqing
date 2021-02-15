import request from '@/utils/request'
export default {
    //1 学生列表（条件查询分页）
    //current当前页 limit每页记录数 studentQuery条件对象
    getStudentListPage(current,limit,studentQuery){
        return request({
            //url: '/eduservice/student/'+current+"/"+limit,
            url: `/servicemain/student/${current}/${limit}`,
            method: 'post',
            //studentQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: studentQuery
          })
    },
    //添加学生
    addStudent(student) {
        return request({
            url: `/servicemain/student/save`,
            method: 'post',
            data: student
            })
    },
    //删除学生
    deleteStudentId(id) {
        return request({
            url: `/servicemain/student/${id}`,
            method: 'delete'
          })
    },
    //修改学生
    updateStudentInfo(student) {
        return request({
            url: `/servicemain/student/updatestudent`,
            method: 'post',
            data: student
          })
    },
    //根据id查询学生
    getStudentInfo(id) {
        return request({
            url: `/servicemain/student/getstudent/${id}`,
            method: 'get'
            })
    }
}
