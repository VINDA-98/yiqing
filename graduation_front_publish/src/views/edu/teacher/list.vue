<template>
  <div class="app-container">
    教师列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="教师名"/>
      </el-form-item>

      <el-form-item>
        <el-input v-model="teacherQuery.account" placeholder="教师账号"/>
      </el-form-item>

      <!-- placeholder：占位符 -->
      <el-form-item> 
        <el-select v-model="teacherQuery.collegeid" clearable placeholder="教师归属学院">
          <el-option
              v-for="item in collegeoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />
      <el-table-column label="性别" prop="sex">
            <template slot-scope="scope">
        {{ scope.row.sex===1?'男':'女'}}
      </template>
      </el-table-column>
      <el-table-column prop="account" label="账号" width="100" />
      <el-table-column prop="name" label="名称" width="80" />
      <el-table-column prop="collegeid" label="学院" width="130"  />
      <el-table-column prop="phone"     label="手机号码" />
      <el-table-column prop="email" label="电子邮箱" width="160"/>
      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <!-- <el-table-column prop="sort" label="排序" width="60" /> -->

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>
<script>
//引入调用teacher.js文件
import teacher from '@/api/edu/teacher'
import college from '@/api/edu/college'

export default {
    //写核心代码位置
    // data:{
    // },
    data(){ //定义变量和初始值
        return {
          list:null,//查询之后接口返回集合
          page:1,//当前页
          limit:10,//每页记录数
          total:0,//总记录数
          teacherQuery:{},//条件封装对象
          collegeoptions:[]
        }
    },
    created() { //页面渲染之前执行，一般调用methods定义的方法
        //调用
        this.getList() 
        this.getCollegeList()
    },
    methods:{  //创建具体的方法，调用teacher.js定义的方法
        //教师列表的方法
        getList(page=1) {
            this.page = page
            teacher.getTeacherListPage(this.page,this.limit,this.teacherQuery)
                .then(response =>{//请求成功
                    //response接口返回的数据
                    //console.log(response)
                    this.list = response.data.rows
                    this.total = response.data.total
                    console.log(this.list)   
                    console.log(this.total)
                }) 
        },
        getCollegeList(){
          college.getCollegeList().then(response=>{
            this.collegeoptions = response.data.colleges 
          })
        },
        resetData() {//清空的方法
            //表单输入项数据清空
            this.teacherQuery = {}
            //查询所有教师数据
            this.getList()
        },
        //删除教师的方法
        removeDataById(id) {
            this.$confirm('此操作将永久删除教师记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {  //点击确定，执行then方法
                //调用删除的方法
                teacher.deleteTeacherId(id)
                    .then(response =>{//删除成功
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //回到列表页面
                    this.getList()
                })
            }) //点击取消，执行catch方法
        }
 
    }
}
</script>
