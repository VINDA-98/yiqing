<template>
  <div class="app-container">
    班级表单
     <el-form label-width="120px">
       
       
      <el-form-item label="班级ID">
        <el-input v-model="myclass.id" placeholder="请输入ID" />
      </el-form-item>

      <el-form-item label="班级名称">
        <el-input v-model="myclass.name" placeholder="请输入名称" />
      </el-form-item>

      <el-form-item label="所属学院">
        <el-select v-model="myclass.collegeid" clearable placeholder="请选择">
          <el-option
              v-for="item in collegeoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>  
      </el-form-item>

      
      <el-form-item label="所属专业">
        <el-select v-model="myclass.majorid" clearable placeholder="请选择">
          <el-option
              v-for="item in majoroptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>  
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>
<script>
import myclassApi from '@/api/edu/class'
import collegeApi from '@/api/edu/college'
import majorApi from '@/api/edu/major'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      myclass:{
        id:'',
        name: '',
        sort: 0,
        collegeid:"",
        majorid:"",
      },
      saveid:'', //需要添加的ID
      collegeoptions:[],
      majoroptions:[],
      value: '',

      //上传弹框组件是否显示
      imagecropperShow:false,
      imagecropperKey:0,//上传组件key值
      BASE_API:process.env.BASE_API, //获取dev.env.js里面地址
      saveBtnDisabled:false  // 保存按钮是否禁用,
    }
  },
  created() { //页面渲染之前执行
    this.init()
    this.getCollegeList()
    this.getMajorList()
  },
  watch: {  //监听
    $route(to, from) { //路由变化方式，路由发生变化，方法就会执行
      this.init()
    }
  },
   rules: {  //自定义规则
     tname: [
        { required: true, message: '请输入活动名称', trigger: 'blur' },
        { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
      ]
    },
  methods:{
    close() { //关闭上传弹框的方法
        this.imagecropperShow=false
        //上传组件初始化
        this.imagecropperKey = this.imagecropperKey+1
    },
    //上传成功方法
    cropSuccess(data) {
      this.imagecropperShow=false
      //上传之后接口返回图片地址
      this.myclass.avatar = data.url
      this.imagecropperKey = this.imagecropperKey+1
    },
    init() {
      //判断路径有id值,做修改
      if(this.$route.params && this.$route.params.id) {
          //从路径获取id值
          const id = this.$route.params.id
          //调用根据id查询的方法
          this.getInfo(id)
          this.saveid = id
      } else { //路径没有id值，做添加
        //清空表单
        this.myclass = {}
      }
    },
    //根据班级id查询的方法
    getInfo(id) {
        myclassApi.getClassInfo(id)
        .then(response => {
          this.myclass = response.data.classbyid
        })
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据myclass是否有id
      if(!this.saveid) {
        //添加
        this.savemyclass()    
      } else {
         //修改
        this.updatemyclass()

      }
    },
    //修改班级的方法
    updatemyclass() {
      myclassApi.updateClassInfo(this.myclass)
        .then(response => {
          //提示信息
          this.$message({
              type: 'success',
              message: '修改成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/myclass/table'})
        })
    },
    //添加班级的方法
    savemyclass() {
      myclassApi.addClass(this.myclass)
        .then(response => {//添加成功
          //提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/myclass/table'})
        })
    },
    getCollegeList(){
      collegeApi.getCollegeList().then(response=>{
        this.collegeoptions = response.data.colleges 
      })  
    },
    getMajorList(){
      majorApi.getMajorList().then(response=>{
      this.majoroptions = response.data.majors 
          })
        },
  }
}
</script>
