<template>
  <div class="app-container">
    教师温度监控
    <br />
    <hr />
    <template>
      <el-radio-group v-model="radio" @change="radioCheck()">
        <el-radio :label="0">最新数据</el-radio>
        <el-radio :label="1">全部数据</el-radio>
      </el-radio-group>
    </template>
    <br />
    <hr />

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="教师姓名">
        <el-input v-model="tempQuery.name" placeholder="教师名" />
      </el-form-item>

      <!-- placeholder：占位符 -->
      <el-form-item label="学院">
        <el-select
          v-model="tempQuery.collegeid"
          clearable
          placeholder="教师归属学院"
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
        >获取三日内所有教师数据</el-button
      >

      <el-button
        type="warning"
        icon="el-icon-user-solid"
        @click="getError1Day()"
        round
        >获取今日教师体温异常数据</el-button
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
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <!-- <el-table-column prop="id" label="温度ID" width="200"/> -->
      <el-table-column prop="name" label="教师名称" />
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
      <el-table-column prop="end" label="更新时间" />

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="
              scope.row.tempresolve === 1 &&
              scope.row.temp != null &&
              scope.row.temp != ''
            "
            type="primary"
            size="mini"
            icon="el-icon-message"
            @click="sendEmail(scope.row.id, scope.row.temp)"
            round
            >发送邮件</el-button
          >

          <el-button
            v-if="
              scope.row.tempresolve === 0 &&
              scope.row.temp != null &&
              scope.row.temp != ''
            "
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
              >查看教师数据</el-button
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
import teacher from "@/api/edu/teacher";
import myclass from "@/api/edu/class";
import major from "@/api/edu/major";
import college from "@/api/edu/college";
//引入调用temp.js文件
import tempApi from "@/api/edu/temp";
//使用echars
import echarts from "echarts";

export default {
  data() {
    //定义变量和初始值
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
      tempQuery: {}, //条件封装对象
      collegeoptions: [],
      tempoptions: [],
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
        .getTempTeacherListPage(
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
      this.tempQuery = {};
    },
    getListNow(page) {
      this.page = page;
      tempApi
        .getTempTeacherListPage(
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
      this.tempQuery = {};
    },
    getCollegeList() {
      college.getCollegeList().then((response) => {
        this.collegeoptions = response.data.colleges;
      });
    },
    resetData() {
      //清空的方法 表单输入项数据清空
      this.tempQuery = {};
      this.getList();
      this.getCollegeList();
    },
    radioCheck() {
      this.mode = this.radio;
      tempApi
        .getTempTeacherListPage(
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
    //监听设备
    ListenTemp() {
      if (this.flagstatic) {
        tempApi.openAMQP().then((response) => {
          //提示信息
          this.$message({
            type: "success",
            message: "开启成功，设备已经在监控工作!",
          });
        });
      } else {
        tempApi.closeAMQP().then((response) => {
          //提示信息
          this.$message({
            type: "success",
            message: "关闭成功，设备已经关闭监控工作!",
          });
        });
      }
    },
    //获取异常温度
    getErrorTemp() {
      this.mode = 1;
      this.radio = 1;
      this.tempQuery.maxtemp = 37.3;
      tempApi
        .getTempTeacherListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          this.list = response.data.rows;
          this.total = response.data.total;
          this.tempQuery = {};
        });
    },
    //发送邮件
    sendEmail(id, temp) {
      //如果是空的，发送空邮件
      if (temp == null || temp == "") {
        tempApi.sendRefreshEmail(id).then((response) => {
          this.$message({
            type: "success",
            message: "向用户发送更新体温警示邮件成功!!!",
          });
          this.getListNow(this.page);
        });
      }
      //温度不为空的时候
      else {
        if (temp >= 37.3) {
          this.$confirm("确认要发送警示邮件?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              tempApi.sendWarringEmail(id).then((response) => {
                this.$message({
                  type: "success",
                  message: "发送邮件成功，已经提醒成功!!!",
                });
                this.getListNow(this.page);
              });
            })
            .catch((error) => {
              console.log(error);
            }); //点击取消，执行catch方法
        } else {
          this.$confirm("用户温度正常，需要发送警示邮件?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              tempApi.sendWarringEmail(id).then((response) => {
                this.$message({
                  type: "success",
                  message: "发送邮件成功，已经提醒成功!!!",
                });
                this.getListNow(this.page);
              });
            })
            .catch((error) => {
              console.log(error);
            }); //点击取消，执行catch方法
        }
      }
    },
    //get3Day() 获取三天所有教师有效数据
    get3Day() {
      this.mode = 1;
      this.radio = 1;
      this.tempQuery.day = 3 //三日有效数据
      tempApi
        .getTempTeacherListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          this.list = response.data.rows;
          this.total = response.data.total;
          this.tempQuery = {};
        });
    },
    //获取今日异常数据
    getError1Day() {
      this.mode = 0;
      this.radio = 1;
      this.tempQuery.maxtemp = 37.3;
      this.tempQuery.day = 1;
      tempApi
        .getTempTeacherListPage(
          this.page,
          this.limit,
          this.mode,
          this.tempQuery
        )
        .then((response) => {
          this.list = response.data.rows;
          this.total = response.data.total;
          this.tempQuery = {};
        });
    },
  },
};
</script>