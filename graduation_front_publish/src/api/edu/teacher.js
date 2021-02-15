import request from '@/utils/request'
export default {
    //1 教师列表（条件查询分页）
    //current当前页 limit每页记录数 teacherQuery条件对象
    getTeacherListPage(current,limit,teacherQuery) {
        return request({
            //url: '/eduservice/teacher/'+current+"/"+limit,
            url: `/servicemain/teacher/${current}/${limit}`,
            method: 'post',
            //teacherQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: teacherQuery
          })
    },
    //添加教师
    addTeacher(teacher) {
        return request({
            url: `/servicemain/teacher/save`,
            method: 'post',
            data: teacher
            })
    },
    //删除教师
    deleteTeacherId(id) {
        return request({
            url: `/servicemain/teacher/${id}`,
            method: 'delete'
          })
    },

    //修改教师
    updateTeacherInfo(teacher) {
        return request({
            url: `/servicemain/teacher/updateTeacher`,
            method: 'post',
            data: teacher
          })
    },
    //根据id查询教师
    getTeacherInfo(id) {
        return request({
            url: `/servicemain/teacher/getTeacher/${id}`,
            method: 'get'
            })
    }
}
