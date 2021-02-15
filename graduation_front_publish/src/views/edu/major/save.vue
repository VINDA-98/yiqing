<template>
  <div class="app-container">
    专业表单
     <el-form label-width="120px">
       
      <el-form-item label="专业ID">
        <el-input v-model="major.id" placeholder="请输入专业名称" />
      </el-form-item>

      <el-form-item label="专业名称">
        <el-input v-model="major.name" placeholder="请输入专业名称" />
      </el-form-item>


      <el-form-item label="所属学院">
        <el-select v-model="major.collegeid" clearable placeholder="请选择">
          <el-option
              v-for="item in collegeoptions"
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
import majorApi from '@/api/edu/major'
import collegeApi from '@/api/edu/college'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      major:{
        id:'',
        name: '',
        sort: 0,
        collegeid:'',
      },
      saveid:'', //需要添加的ID
      input:'',
      collegeoptions:[],
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
  },
  watch: {  //监听
    $route(to, from) { //路由变化方式，路由发生变化，方法就会执行
      this.init()
    }
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
      this.major.avatar = data.url
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
        this.major = {}
      }
    },
    //根据专业id查询的方法
    getInfo(id) {
      majorApi.getMajorInfo(id)
        .then(response => {
          this.major = response.data.major
        })
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据major是否有id
      if(!this.saveid) {
        //添加
        this.saveMajor()
      } else {
                 //修改
        this.updateMajor()

      }
    },
    //修改专业的方法
    updateMajor() {
      majorApi.updateMajorInfo(this.major)
        .then(response => {
          //提示信息
          this.$message({
              type: 'success',
              message: '修改成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/major/table'})
        })
    },
    //添加专业的方法
    saveMajor() {
      majorApi.addMajor(this.major)
        .then(response => {//添加成功
          //提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/major/table'})
        })
    },
    getCollegeList(){
      collegeApi.getCollegeList().then(response=>{
        this.collegeoptions = response.data.colleges 
      })  
    },
  }
}
</script>
