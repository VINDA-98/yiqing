<template>
  <div class="app-container">
    教师表单
     <el-form label-width="120px">
       
      <el-form-item label="教师名称">
        <el-input v-model="teacher.name" placeholder="请输入姓名" />
      </el-form-item>

     <el-form-item label="登录账号">
        <el-input v-model="teacher.account" placeholder="请输入学号"/>
      </el-form-item>
     
     <el-form-item label="登录密码">
        <el-input placeholder="请输入密码" v-model="teacher.password" type='Password' show-password></el-input>
     </el-form-item>


      <el-form-item label="教师年龄">
        <el-input v-model="teacher.age"/>
      </el-form-item>

      <el-form-item label="教师性别">
        <el-select v-model="teacher.sex" clearable placeholder="请选择">
          <el-option
              v-for="item in sexoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="所属学院">
        <el-select v-model="teacher.collegeid" clearable placeholder="请选择">
          <el-option
              v-for="item in collegeoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>  
      </el-form-item>

      <el-form-item label="教师电话">
        <el-input v-model="teacher.phone"/>
      </el-form-item>

      <el-form-item label="教师邮箱">
        <el-input v-model="teacher.email"/>
      </el-form-item>


      <!-- 教师头像：TODO -->
      <!-- 教师头像 -->
      <el-form-item label="教师头像">

          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>
          <!--
          v-show：是否显示上传组件
          :key：类似于id，如果一个页面多个图片上传控件，可以做区分
          :url：后台上传的url地址
          @close：关闭上传组件@crop-upload-success：上传成功后的回调 <input type="file" name="file"/>
          -->
          <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduoss/fileoss'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="sendMqtt">认证</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>
<script>
import tempApi from '@/api/edu/temp'
import teacherApi from '@/api/edu/teacher'
import collegeApi from '@/api/edu/college'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher:{
        name: '',
        sort: 0,
        avatar: '',
        sex: 1,
        age: "0",
        phone:"0",
        email: "",
        collegeid:"",
      },
      mqttItem:"", //发送给MQTT的内容
      mqttData:"",
      input:'',
      collegeoptions:[],
      sexoptions: [{
          value: 1,
          label: '男'}, {
          value: 2,
          label: '女'
        }],
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
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey+1
    },
    init() {
      //判断路径有id值,做修改
      if(this.$route.params && this.$route.params.id) {
          //从路径获取id值
          const id = this.$route.params.id
          //调用根据id查询的方法
          this.getInfo(id)
      } else { //路径没有id值，做添加
        //清空表单
        this.teacher = {}
      }
    },
        //发送认证方法
    sendMqtt(){
        this.mqttItem = "photo";
        this.mqttData = this.teacher.name;
        if(this.mqttData != null && this.mqttData != ''){
            console.log(this.mqttItem  +"  " + this.mqttData);
            tempApi.sendMqtt(this.mqttItem,this.mqttData).then(response => {
                //提示信息
              this.$message({
                  type: 'success',
                  message: '认证成功!'
              });
          })
        }else{
            this.$notify({
              title: '提示',
              message: '请完善用户相关信息后再认证',
              position: 'top-left'
            });
        }
    },
    //根据教师id查询的方法
    getInfo(id) {
      teacherApi.getTeacherInfo(id)
        .then(response => {
          this.teacher = response.data.teacher
        })
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据teacher是否有id
      if(!this.teacher.id) {
        //添加
        this.saveTeacher()
      } else {
        //修改
        this.updateTeacher()
      }
    },
    //修改教师的方法
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher,this.teacher.id)
        .then(response => {
          //提示信息
          this.$message({
              type: 'success',
              message: '修改成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/teacher/table'})
        })
    },
    //添加教师的方法
    saveTeacher() {
      if(this.teacher.name != null
         && this.teacher.account != null
         && this.teacher.password != null
         && this.teacher.sex != null
         && (this.teacher.email != null || this.teacher.phone != null )
         ){
            teacherApi.addTeacher(this.teacher)
            .then(response => {//添加成功
              //提示信息
              this.$message({
                  type: 'success',
                  message: '添加成功!'
              });
              //回到列表页面 路由跳转
              this.$router.push({path:'/teacher/table'})
            })
         }else{
              this.$notify({
              title: '提示',
              message: '请完善用户相关信息后再保存',
              position: 'top-left'
            });
         }
    },
    getCollegeList(){
      collegeApi.getCollegeList().then(response=>{
        this.collegeoptions = response.data.colleges 
      })  
    },
  }
}
</script>
