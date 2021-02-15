<template>
  <div class="app-container">
    学生温度监控
    <br />
    <hr />
    <template>
      <el-radio-group v-model="radio" @change="radioCheck()">
        <el-radio :label="0">最新数据</el-radio>
        <el-radio :label="1">全部数据</el-radio>
      </el-radio-group>
    </template>

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="学生姓名">
        <el-input v-model="tempQuery.name" placeholder="学生名" />
      </el-form-item>

      <!-- placeholder：占位符 -->
      <el-form-item label="学院">
        <el-select
          v-model="tempQuery.collegeid"
          clearable
          placeholder="学生归属学院"
          @change="checkCollege(tempQuery.collegeid)"
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
          v-model="tempQuery.majorid"
          clearable
          placeholder="所属专业"
          @change="checkMajor(tempQuery.majorid)"
        >
          <el-option
            v-for="item in majoroptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
            {{ item.name }}
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="班级">
        <el-select
          v-model="tempQuery.classid"
          clearable
          placeholder="所属班级"
          @change="checkClass(tempQuery.classid)"
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
      <el-form-item label="监控时间范围">
        <el-date-picker
          v-model="tempQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="tempQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item label="温度范围">
        <el-input v-model="tempQuery.maxtemp" placeholder="最高温度" />
      </el-form-item>

      <el-form-item>
        <el-input v-model="tempQuery.mintemp" placeholder="最低温度" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()" round
        >查询</el-button
      >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <el-button
        type="default"
        icon="el-icon-delete-solid"
        @click="resetData()"
        round
        >清空</el-button
      >

      <br />
      <el-button type="info" icon="el-icon-user-solid" @click="get3Day()" round
        >获取三日内所有学生数据</el-button
      >
      <el-button
        type="warning"
        icon="el-icon-user-solid"
        @click="getError1Day()"
        round
        >获取今日学生体温异常数据</el-button
      >
      <el-button
        type="warning"
        icon="el-icon-warning"
        @click="getErrorTemp()"
        round
        >一键获取异常温度(默认37.3°C,七天范围内)</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-s-promotion"
        @click="sendAllEmail()"
        round
        >一键发送邮件警告</el-button
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

     <!-- <el-table-column prop="id" label="温度ID"/> 
     <el-table-column prop="userid" label="温度ID" />  -->
      <el-table-column prop="name" label="学生名称" />
      <el-table-column label="温度/°C">
        <template slot-scope="scope">
          <span v-if="scope.row.temp >= 37.3" style="color: red">{{
            scope.row.temp
          }}</span>
          <span v-if="scope.row.temp < 37.3" style="color: green">{{
            scope.row.temp
          }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="collegeid" label="所属学院" />
      <el-table-column prop="majorid" label="所属专业" />
      <el-table-column prop="classid" label="所属班级" />
      <el-table-column prop="end" label="更新时间" width="180px"/>

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.tempresolve === 1 && scope.row.temp != null && scope.row.temp != ''"
            type="primary"
            size="mini"
            icon="el-icon-message"
            @click="sendEmail(scope.row.id, scope.row.temp)"
            round
            >发送邮件</el-button
          >

          <el-button
            v-if="scope.row.tempresolve === 0 && scope.row.temp != null && scope.row.temp != ''"
            type="success"
            size="mini"
            icon="el-icon-message"
            disabled
            @click="sendEmail(scope.row.id, scope.row.temp)"
            round
            >已经处理</el-button
          >

          <el-button
            v-if="scope.row.temp === null || scope.row.temp === ''"
            type="warning"
            size="mini"
            icon="el-icon-message"
            @click="sendEmail(scope.row.id, scope.row.temp)"
            round
            >更新邮件</el-button
          >
        </template>
      </el-table-column>

      <el-table-column align="center" label="查看">
        <template slot-scope="scope">
          <router-link :to="'/temp/show/' + scope.row.userid">
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
//引入CSS
import "@/views/edu/temp/css/showtemp.css";
import student from "@/api/edu/student";
import myclass from "@/api/edu/class";
import major from "@/api/edu/major";
import college from "@/api/edu/college";
//引入调用temp.js文件
import tempApi from "@/api/edu/temp";
//使用echars
import echarts from "echarts";

export default {
  //写核心代码位置
  // data:{
  // },

  data() {
    //定义变量和初始值
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
      tempQuery: {}, //条件封装对象
      collegeoptions: [],
      majoroptions: [],
      classoptions: [],
      tempoptions: [],
      checkAll: false,
      checkedPerson: ["最新数据"],
      isIndeterminate: true,
      mode: 0,
      radio: 0,
    };
  },
  created() {
    //页面渲染之前执行，一般调用methods定义的方法
    //调用
    this.getList();
    this.getCollegeList();
    this.getMajorList();
    this.getClassList();
  },
  methods: {
    //创建具体的方法，调用temp.js定义的方法
    remake_change: function (row) {
      if (row.row.temp >= 37.3) {
        return "table-info-row";
      }
    },
    //温度列表的方法
    getList(page = 1) {
      this.page = page;
      tempApi
        .getTempStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          //请求成功
          this.list = response.data.rows;
          this.total = response.data.total;
        });
    },
    getListNow(page) {
      this.page = page;
      tempApi
        .getTempStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          //请求成功
          this.list = response.data.rows;
          this.total = response.data.total;
        });
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
      this.tempQuery = {};
      //查询所有温度数据
      this.getList();
      this.getCollegeList();
      this.getMajorList();
      this.getClassList();
    },
    radioCheck() {
      this.mode = this.radio;
      tempApi
        .getTempStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          //请求成功
          this.list = response.data.rows;
          this.total = response.data.total;
        });
    },

    //获取异常温度
    getErrorTemp() {
      this.mode = 1;
      this.radio = 1;
      this.tempQuery.maxtemp = 37.3;
      tempApi
        .getTempStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          this.list = response.data.rows;
          this.total = response.data.total;
          console.log(this.list);
          console.log(this.total);
          this.tempQuery = {};
        });
    },
    //发送邮件
    sendEmail(id, temp) {
      //如果是空的，发送空邮件
      if (temp == null || temp == '') {
        tempApi.sendRefreshEmail(id).then((response) => {
              this.$message({
                type: "success",
                message: "向用户发送更新体温警示邮件成功!!!",
              });
              this.getListNow(this.page);
            });
      }
      //温度不为空的时候
      else{
        if (temp >= 37.3) {
          this.$confirm("确认要发送警示邮件?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            tempApi.sendWarringEmail(id).then((response) => {
              this.$message({
                type: "success",
                message: "发送邮件成功，已经提醒成功!!!",
              });
              this.getListNow(this.page);
            });
          })
        } else {
          this.$confirm("用户温度正常，需要发送警示邮件?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            tempApi.sendWarringEmail(id).then((response) => {
              this.$message({
                type: "success",
                message: "发送邮件成功，已经提醒成功!!!",
              });
              this.getListNow(this.page);
            });
          })
        }
      }
    },
    //获取三天所有学生有效数据
    get3Day() {
      this.mode = 1;
      this.radio = 1;
      this.tempQuery.day = 3
      tempApi
        .getTempStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          this.list = response.data.rows;
          this.total = response.data.total;
        });
    },
    //获取今日异常数据
    getError1Day() {
      this.mode = 0;
      this.radio = 1;
      this.tempQuery.maxtemp = 37.3;
      this.tempQuery.day = 1;
      tempApi
        .getTempStudentListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          this.list = response.data.rows;
          this.total = response.data.total;
          console.log(this.list);
          console.log(this.total);
          this.tempQuery = {};
        });
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