<template>
  <div class="app-container">
    学生行程监控
    <br />
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="数据选择:">
        <el-radio-group v-model="radio" @change="radioCheck()">
          <el-radio :label="0">最新数据</el-radio>
          <el-radio :label="1">全部数据</el-radio>
        </el-radio-group>
    </el-form-item>
    <hr />
      <el-form-item label="学生姓名">
        <el-input v-model="tripQuery.name" placeholder="学生名" />
      </el-form-item>

      <!-- placeholder：占位符 -->
      <el-form-item label="学院">
        <el-select
          v-model="tripQuery.collegeid"
          clearable
          placeholder="学生归属学院"
          @change="checkCollege(tripQuery.collegeid)"
        >
          <el-option
            v-for="item in collegeoptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
            {{ item.name }}
          </el-option>
        </el-select>
      </el-form-item>

    <el-form-item label="专业">
       <el-select
          v-model="tripQuery.majorid"
          clearable
          placeholder="所属专业"
          @change="checkMajor(tripQuery.majorid)">
          <el-option
            v-for="item in majoroptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
            {{ item.name }}
          </el-option>
      </el-select>
    </el-form-item>

      <el-form-item label="班级">
        <el-select
          v-model="tripQuery.classid"
          clearable
          placeholder="所属班级"
          @change="checkClass(tripQuery.classid)"
        >
          <el-option
            v-for="item in classoptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
            {{ item.name }}
          </el-option>
        </el-select>
        </el-form-item>
    <br />
     <el-form-item label="选择地区:">
      <!-- <template>  -->
        <v-distpicker :province="select.province" :city="select.city" :area="select.area"
                       @province="onChangeProvince"  @city="onChangeCity"  @area="onChangeArea" 
        ></v-distpicker>
       <!-- <template>  -->
    </el-form-item>
  
      <el-form-item label="选择监控行程时间范围">
        <el-date-picker
          v-model="tripQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="tripQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()" round
        >查询</el-button>

      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="default" icon="el-icon-delete-solid" @click="resetData()" round>清空</el-button>

      <br />
      <el-button type="info" icon="el-icon-user-solid" @click="get3Day()" round>
          获取三日内所有学生数据</el-button>
      <el-button
        type="warning"
        icon="el-icon-user-solid"
        @click="getError1Day()" round
        >获取今日学生行程异常数据</el-button
      >

      <el-button type="warning" icon="el-icon-warning" @click="getErrorTrip()" round
        >一键获取异常行程</el-button
      >

    </el-form>
    <br />

    <!-- 表格 -->
    <el-table
      :data="list"
      :row-class-name="remake_change"
      border
      fit
      highlight-current-row
      style="text-align"
    >
      <el-table-column label="序号" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <!-- <el-table-column prop="id" label="行程ID" width="200"/> -->
      <el-table-column prop="name" label="学生名称"  />
      <el-table-column label="行程信息" width="250px" prop="trip">
      </el-table-column>

      <el-table-column prop="collegeid" label="所属学院" />
      <el-table-column prop="majorid" label="所属专业" />
      <el-table-column prop="classid" label="所属班级"  />
      <el-table-column prop="tripend" label="更新时间"  width="160px"/>

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.tripreslove === 1 && scope.row.trip != null && scope.row.trip != ''"
            type="primary"
            size="mini"
            icon="el-icon-message"
            @click="sendEmail(scope.row.id, scope.row.trip)"
            round
            >发送邮件</el-button
          >

          <el-button
            v-if="scope.row.tripreslove === 0 && scope.row.trip != null && scope.row.trip != ''"
            type="success"
            size="mini"
            icon="el-icon-message"
            disabled
            @click="sendEmail(scope.row.id, scope.row.trip)"
            round
            >已经处理</el-button
          >

          <el-button
            v-if="scope.row.trip === null || scope.row.trip === ''"
            type="warning"
            size="mini"
            icon="el-icon-message"
            @click="sendEmail(scope.row.id, scope.row.trip)"
            round
            >更新邮件</el-button
          >
        </template>
      </el-table-column>

      <el-table-column  align="center" label="查看">
        <template slot-scope="scope">
          <router-link :to="'/trip/show/' + scope.row.userid">
            <el-button type="danger" size="mini" icon="el-icon-document" round
              >查看学生数据</el-button
            >
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
//导入 VDistpicker
import VDistpicker from "v-distpicker";
//引入CSS
import "@/views/edu/trip/css/showtrip.css";
import student from "@/api/edu/student";
import myclass from "@/api/edu/class";
import major from "@/api/edu/major";
import college from "@/api/edu/college";
//引入调用temp.js文件
import tripApi from "@/api/edu/trip";
//使用echars
import echarts from "echarts";
//导入行程
import { regionData, CodeToText } from "element-china-area-data";
//导入疫情后台服务
import yiqingApi from "@/api/edu/yiqing";

export default {
  //写核心代码位置
  // data:{
  // },

  components: { VDistpicker },
  name: "",
  data() {
    //定义变量和初始值
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
      tripQuery: {}, //条件封装对象
      collegeoptions: [],
      majoroptions: [],
      classoptions: [],
      tempoptions: [],
      checkAll: false,
      checkedPerson: ["最新数据"],
      isIndeterminate: true,
      mode: 0,
      radio: 0,
      options: regionData,
      selectedOptions: [],
      addtions: {}, //存储地址数据
      select: { province: "湖北省", city: "武汉市", area: "区" },//默认选择
      warringCity:[], //存在高风险城市列表
    };
  },
  created() {
    //页面渲染之前执行，一般调用methods定义的方法
    //调用
    this.getList();
    this.getCollegeList();
    this.getMajorList();
    this.getClassList();
     //获取各个省市疫情，对比疫情信息
    this.getNationalProvinces();
    
  },
  methods: {
    //创建具体的方法，调用temp.js定义的方法
    //修改表格颜色，突出严重地区的疫情数据
    remake_change: function (row) {
      if (row.row.trip != null && row.row.trip != '') {
        if(row.row.trip.indexOf("湖北") >= 0){
          return "table-info-row";
        }
      }
    },
    //获取各个省市疫情，对比疫情信息
    getNationalProvinces(){
      yiqingApi.getNationalProvincesCurrentconfirmed().then((response) => {
          //请求成功
          for(var i = 0 ; i<response.data.provinceMapVO.provinces.length; i++){
           
            //最近新增人数大于等于10的需要警告
            if(response.data.provinceMapVO.provinces[i].value >= 10){
                this.warringCity.push(response.data.provinceMapVO.provinces[i].name)
            }
          }
          console.log(this.warringCity)
        });
    },
    //更改省会
    onChangeProvince(data){
      this.tripQuery.mode = this.radio
      this.tripQuery.province = data.value
    },
     //更改城市
    onChangeCity(data){
      this.tripQuery.mode = this.radio
      this.tripQuery.city = data.value
    },
    //更改县城
    onChangeArea(data){
      this.tripQuery.mode = this.radio
      this.tripQuery.province = data.value
    },
    //行程列表的方法
    getList(page = 1) {
      this.page = page;
      this.mode = this.radio
      tripApi
        .getTripStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tripQuery
        )
        .then((response) => {
          //请求成功
          this.list = response.data.rows;
          this.total = response.data.total;
        });
      this.tripQuery = {};
    },
    getListNow(page) {
      this.page = page;
      tripApi
        .getTripStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tripQuery
        )
        .then((response) => {
          //请求成功
          this.list = response.data.rows;
          this.total = response.data.total;
        });
      this.tripQuery = {};
    },

    getCollegeList() {
      college.getCollegeList().then((response) => {
        this.collegeoptions = response.data.colleges;
      });
    },
    getMajorList() {
      major.getMajorList().then((response) => {
        this.majoroptions = response.data.majors;
      });
    },
    getClassList() {
      myclass.getClassList().then((response) => {
        this.classoptions = response.data.myclass;
      });
    },
    resetData() {
      //清空的方法
      //表单输入项数据清空
      this.tripQuery = {};
      //查询所有行程数据
      this.getList();
      this.getCollegeList();
      this.getMajorList();
      this.getClassList();
    },
    radioCheck() {
      this.mode = this.radio;
      tripApi
        .getTripStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tripQuery
        )
        .then((response) => {
          //请求成功
          this.list = response.data.rows;
          this.total = response.data.total;
        });
    },
    //监听设备
    ListenTemp() {
      if (this.flagstatic) {
        tripApi.openAMQP().then((response) => {
          //提示信息
          this.$message({
            type: "success",
            message: "开启成功，设备已经在监控工作!",
          });
        });
      } else {
        tripApi.closeAMQP().then((response) => {
          //提示信息
          this.$message({
            type: "success",
            message: "关闭成功，设备已经关闭监控工作!",
          });
        });
      }
    },

    //获取异常行程
    getErrorTrip() {

    },
    //发送邮件
    sendEmail(id, trip) {
    //如果是空的，发送空邮件
      if (trip == null || trip == '') {
        tripApi.sendRefreshEmail(id).then((response) => {
              this.$message({
                type: "success",
                message: "向用户发送更新行程提示邮件成功!!!",
              });
              this.getListNow(this.page);
            });
      }
      //行程不为空的时候
      else{
          tripApi.sendWarringEmail(id).then((response) => {
            this.$message({
              type: "success",
              message: "发送邮件成功，已经提醒成功!!!",
            });
            this.getListNow(this.page);
          });
        }
    },
    //获取三天所有学生有效数据
    get3Day() {
      this.mode = 1;
      this.radio = 1;
      this.tripQuery.day = 3
      tripApi
        .getTripStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tripQuery
        )
        .then((response) => {
          this.list = response.data.rows;
          this.total = response.data.total;
        });
    },
    //获取今日异常数据
    getError1Day() {

    },

    //当选中了某个学院，更新专业列表和班级列表
    checkCollege(cid) {
      if (cid != null) {
        //更新专业
        major.getMajorInfoByCollegeID(cid).then((response) => {
          this.majoroptions = response.data.majors;
        }),
          //更新班级
          myclass.getClassInfoByCollegeID(cid).then((response) => {
            this.classoptions = response.data.myclass;
          });
      }
    },
    //当选中了某个专业，更新学院列表和班级列表
    checkMajor(cid) {
      if (cid != null) {
        //更新学院
        major.getByCollegeByMajorID(cid).then((response) => {
          this.collegeoptions = response.data.colleges;
        }),
          //更新班级
          myclass.getClassInfoByMajorID(cid).then((response) => {
            this.classoptions = response.data.myclass;
          });
      }
    },
    //当选中了某个班级，更新学院列表和专业列表
    checkClass(cid) {
      if (cid != null) {
        //更新学院与专业
        myclass.getByCollegeAndMajorByClassID(cid).then((response) => {
          this.collegeoptions = response.data.colleges;
          this.majoroptions = response.data.majors;
        });
      }
    },
  },
};
</script>