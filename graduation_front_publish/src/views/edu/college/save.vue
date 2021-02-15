<template>
  <div class="app-container">
    学院表单
     <el-form label-width="120px">
       
      <el-form-item label="学院ID">
        <el-input v-model="mycollege.id"/>
      </el-form-item>

      <el-form-item label="学院名称">
        <el-input v-model="mycollege.name" placeholder="请输入学院名称" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

import collegeApi from '@/api/edu/college'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      mycollege:{
        id:'',
        name:''
      },
      saveid:'', //需要添加的ID
      input:'',
      //上传弹框组件是否显示
      imagecropperShow:false,
      imagecropperKey:0,//上传组件key值
      BASE_API:process.env.BASE_API, //获取dev.env.js里面地址
      saveBtnDisabled:false  // 保存按钮是否禁用,
    }
  },
  created() { //页面渲染之前执行
    this.init()
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
      this.mycollege.avatar = data.url
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
        this.mycollege = {}
      }
    },
    //根据学院id查询的方法
    getInfo(id) {
      collegeApi.getCollegeInfo(id)
        .then(response => {
          this.mycollege = response.data.College
        })
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据college是否有id
      if(!this.saveid) {
        //添加
        this.savecollege()
      } else {
        //修改
        this.updatecollege()
      }
    },
        //添加学院的方法
    savecollege() {
      collegeApi.addCollege(this.mycollege)
        .then(response => {//添加成功
          //提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/college/table'})
        })
    },
    //修改学院的方法 
    updatecollege() {
      collegeApi.updateCollegeInfo(this.mycollege)
        .then(response => {
          //提示信息
          this.$message({
              type: 'success',
              message: '修改成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/college/table'})
        })
    },
  }
}
</script>
