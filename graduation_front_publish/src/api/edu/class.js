import request from '@/utils/request'
export default {
    //1 班级列表（条件查询分页）
    //current当前页 limit每页记录数 classQuery条件对象
    getClassListPage(current,limit,classQuery){
        return request({
            //url: '/eduservice/class/'+current+"/"+limit,
            url: `/servicemain/class/${current}/${limit}`,
            method: 'post',
            //classQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: classQuery
          })
    },
    //添加班级
    addClass(classobj) {
        return request({
            url: `/servicemain/class/save`,
            method: 'post',
            data: classobj
            })
    },
    //删除班级
    deleteClassId(id) {
        return request({
            url: `/servicemain/class/${id}`,
            method: 'delete'
          })
    },
    //修改班级
    updateClassInfo(classobj) {
        return request({
            url: `/servicemain/class/updateclass`,
            method: 'post',
            data: classobj
          })
    }, 
    //根据id查询班级
    getClassInfo(id) {
        return request({
            url: `/servicemain/class/getclass/${id}`,
            method: 'get'
            })
    },
    //获取所有得班级信息列表
    getClassList(){
        return request({
            url: `/servicemain/class/getallclass/`,
            method: 'get'
        })
    },
    //根据学院ID获取班级信息
    getClassInfoByCollegeID(id){
        return request({
            url: `/servicemain/class/getclassbycollegeid/${id}`,
            method: 'get'
        })
    },
    //根据专业ID获取班级信息
    getClassInfoByMajorID(id){
        return request({
            url: `/servicemain/class/getclassbymajorid/${id}`,
            method: 'get'
        })
    },
    
    //根据班级ID得到对应专业和对应学院
    getByCollegeAndMajorByClassID(id){
        return request({
            url: `/servicemain/class/getbycollegeandmajorbyclassid/${id}`,
            method: 'get'
        })
    },

}
