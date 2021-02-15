<template>
  <div class="app-container">
    学生表单
     <el-form label-width="120px">
       
      <el-form-item label="学生名称">
        <el-input v-model="student.name" placeholder="请输入姓名" />
      </el-form-item>

     <el-form-item label="登录账号">
        <el-input v-model="student.account" placeholder="请输入学号"/>
      </el-form-item>
     
     <el-form-item label="登录密码">
        <el-input placeholder="请输入密码" v-model="student.password" type='Password' show-password></el-input>
     </el-form-item>

      <el-form-item label="学生年龄">
        <el-input v-model="student.age"/>
      </el-form-item>

      <el-form-item label="学生性别">
        <el-select v-model="student.sex" clearable placeholder="请选择">
          <el-option
              v-for="item in sexoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="所属学院">
        <el-select v-model="student.collegeid" clearable placeholder="请选择">
          <el-option
              v-for="item in collegeoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>  
      </el-form-item>


        <el-form-item label="所属专业">
        <el-select v-model="student.majorid" clearable placeholder="请选择">
          <el-option
              v-for="item in majoroptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>  
      </el-form-item>

      <el-form-item label="所属班级">
        <el-select v-model="student.classid" clearable placeholder="请选择">
          <el-option
              v-for="item in classoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>  
      </el-form-item>

      <el-form-item label="学生电话">
        <el-input v-model="student.phone"/>
      </el-form-item>

      <el-form-item label="学生邮箱">
        <el-input v-model="student.email"/>
      </el-form-item>

     <!-- 
      <el-form-item label="学生排序">
        <el-input-number v-model="student.sort" controls-position="right" min="0"/>
      </el-form-item>
      -->

      <!-- 学生头像：TODO -->
      <!-- 学生头像 -->
      <el-form-item label="学生头像">

          <!-- 头衔缩略图 -->
          <pan-thumb :image="student.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>
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
import studentApi from '@/api/edu/student'
import collegeApi from '@/api/edu/college'
import classApi from '@/api/edu/class'
import majorApi from '@/api/edu/major'
import tempApi from '@/api/edu/temp'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      student:{
        name: '',
        sort: 0,
        avatar: '',
        sex: 1,
        age: "0",
        phone:"0",
        email: "",
        collegeid:"",
      },
      input:'',
      collegeoptions: [],
      majoroptions: [],
      classoptions: [],
      value: '',
      sexoptions: [{
          value: 1,
          label: '男'
        }, {
          value: 2,
          label: '女'
        }],
      mqttItem:"", //发送给MQTT的内容
      mqttData:"",
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
    this.getClassList()
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
    //发送认证方法
    sendMqtt(){
        this.mqttItem = "photo";
        this.mqttData = this.student.name;
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
    //上传成功方法
    cropSuccess(data) {
      this.imagecropperShow=false
      //上传之后接口返回图片地址
      this.student.avatar = data.url
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
        this.student = {}
      }
    },
    //根据学生id查询的方法
    getInfo(id) {
      studentApi.getStudentInfo(id)
        .then(response => {
          this.student = response.data.student
        })
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据student是否有id
      if(!this.student.id) {
        //添加
        this.saveStudent()
      } else {
        //修改
        this.updateStudent()
      }
    },
    //修改学生的方法
    updateStudent() {
      studentApi.updateStudentInfo(this.student,this.student.id)
        .then(response => {
          //提示信息
          this.$message({
              type: 'success',
              message: '修改成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/student/table'})
        })
    },
    //添加学生的方法
    saveStudent() {
      if(this.student.name != null
         && this.student.account != null
         && this.student.password != null
         && this.student.sex != null
         && (this.student.email != null || this.student.phone != null )
         ){
          studentApi.addStudent(this.student)
            .then(response => {//添加成功
              //提示信息
              this.$message({
                  type: 'success',
                  message: '添加成功!'
              });
              //回到列表页面 路由跳转
              this.$router.push({path:'/student/table'})
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
    getMajorList(){
        majorApi.getMajorList().then(response=>{
          this.majoroptions = response.data.majors
  
        })
      },
   getClassList(){
        classApi.getClassList().then(response=>{
        this.classoptions = response.data.myclass
  
      })
    }

  }
}
</script>
