<template>
  <div class="app-container">
    学生列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="studentQuery.name" placeholder="学生名"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model="studentQuery.account" placeholder="学生账号"/>
      </el-form-item>

      <!-- placeholder：占位符 -->
      <el-form-item> 
        <el-select v-model="studentQuery.collegeid" 
                    clearable placeholder="学生归属学院" @change="checkCollege(studentQuery.collegeid)">
          <el-option
              v-for="item in collegeoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
              {{item.name}}
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item> 
        <el-select v-model="studentQuery.majorid" clearable placeholder="所属专业" @change="checkMajor(studentQuery.majorid)" >
          <el-option
              v-for="item in majoroptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
              {{item.name}}
          </el-option>
        </el-select>
      </el-form-item>


      <el-form-item> 
        <el-select v-model="studentQuery.classid" clearable placeholder="所属班级" @change="checkClass(studentQuery.classid)">
          <el-option
              v-for="item in classoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
              {{item.name}}
          </el-option>
        </el-select>
      </el-form-item>


      <el-form-item label="添加时间">
        <el-date-picker
          v-model="studentQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="studentQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"/>
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

      <el-table-column label="性别" prop="sex" width="50">
            <template slot-scope="scope">
        {{ scope.row.sex===1?'男':'女'}}
      </template>
      </el-table-column>

      <el-table-column prop="account" label="学号" width="100" />
      <el-table-column prop="name" label="姓名" width="80" />
      <el-table-column prop="collegeid" label="学院" width="130"  />
      <el-table-column prop="majorid"   label="专业" width="130"/>
      <el-table-column prop="classid"   label="班级" width="130"/>
      <el-table-column prop="phone"     label="手机号码" width="130" />
      <el-table-column prop="email" label="电子邮箱" width="160"/>
      <!-- <el-table-column prop="gmtCreate" label="添加时间" width="160"/> -->
      <!-- <el-table-column prop="sort" label="排序" width="60" /> -->
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/student/edit/'+scope.row.id">
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
//引入调用student.js文件
import student from '@/api/edu/student'
import myclass from '@/api/edu/class'
import major from '@/api/edu/major'
import college from '@/api/edu/college'

export default {
    //写核心代码位置
    // data:{
    // },
    data() { //定义变量和初始值
        return {
          list:null,//查询之后接口返回集合
          listcolleges:null,//查询之后接口返回集合
          page:1,//当前页
          limit:10,//每页记录数
          total:0,//总记录数
          studentQuery:{}, //条件封装对象
          collegeoptions:[],
          majoroptions: [],
          classoptions: [],
        }
    },
    created() { //页面渲染之前执行，一般调用methods定义的方法
        //调用
        this.getList() 
        this.getCollegeList()
        this.getMajorList()
        this.getClassList()
    },
    methods:{  //创建具体的方法，调用student.js定义的方法
        //学生列表的方法
        getList(page=1) {
            this.page = page
            student.getStudentListPage(this.page,this.limit,this.studentQuery)
                .then(response =>{//请求成功
                    //response接口返回的数据
                    this.list = response.data.rows
                    this.total = response.data.total
                    console.log("list"+this.list)   
                    console.log("total"+this.total)
                }) 
        },
        getCollegeList(){
          college.getCollegeList().then(response=>{
            this.collegeoptions = response.data.colleges 
          })
        },
        getMajorList(){
          major.getMajorList().then(response=>{
            this.majoroptions = response.data.majors  
          })
        },
        getClassList(){
          myclass.getClassList().then(response=>{
            this.classoptions = response.data.myclass  
          })
        },
        resetData() {//清空的方法
          //表单输入项数据清空
          this.studentQuery = {}
          //查询所有学生数据
           this.getList()
           this.getCollegeList()
           this.getMajorList()
           this.getClassList()
        },
        //删除学生的方法
        removeDataById(id) {
            this.$confirm('此操作将永久删除学生记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {  //点击确定，执行then方法
                //调用删除的方法
                student.deleteStudentId(id)
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
        },

        //当选中了某个学院，更新专业列表和班级列表
        checkCollege(cid){
          if(cid != null){
              //更新专业
              major.getMajorInfoByCollegeID(cid).then(response =>{//删除成功
                      this.majoroptions = response.data.majors
                    }),
              //更新班级
              myclass.getClassInfoByCollegeID(cid).then(response =>{//删除成功
                      this.classoptions = response.data.myclass
                  })
                }
          },
        //当选中了某个专业，更新学院列表和班级列表
        checkMajor(cid){
          if(cid != null){
              //更新学院
              major.getByCollegeByMajorID(cid).then(response =>{
                      this.collegeoptions = response.data.colleges
                  
                    }),
               //更新班级
              myclass.getClassInfoByMajorID(cid).then(response =>{
                      this.classoptions = response.data.myclass
                  })
                }
          },
          //当选中了某个班级，更新学院列表和专业列表
          checkClass(cid){
            if(cid != null){
                //更新学院与专业
                   myclass.getByCollegeAndMajorByClassID(cid).then(response =>{
                        this.collegeoptions = response.data.colleges
                        this.majoroptions = response.data.majors
                      })
                  }
            },

          //todo : 班级 保持列表
    }
}
</script>
