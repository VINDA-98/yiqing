import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  //系统首页
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '疫情管理系统首页',
    meta: { title: '疫情管理系统首页', icon: 'international'},
    //hidden: true,
    children: [
      {
      path: 'dashboard',
      //component: () => import('@/views/dashboard/index'),
      component: () => import('@/views/dashboard/welcome'),
      meta: { title: '疫情管理系统首页', icon: 'international' }
    },
    // {
    //   path: '/dashboard/welcome',
    //   component: () => import('@/views/dashboard/welcome'), //npm install --save vue-aplayer  npm install --save hls.js
    //   meta: { title: '首页', icon: 'international' }
    // },
  ]
  },

  //学院管理
  {
    path: '/college',
    component: Layout,
    redirect: '/college/table',
    name: '学院管理',
    meta: { title: '学院管理', icon: 'xueyuan'},
    children: [
      {
        path: 'table',
        name: '学院列表',
        component: () => import('@/views/edu/college/list'),
        meta: { title: '学院列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加学院',  
        component: () => import('@/views/edu/college/save'),
        meta: { title: '添加学院', icon: 'edit' }
      },
      {
        path: 'edit/:id',   
        name: 'EduCollegeEdit',
        component: () => import('@/views/edu/college/save'),
        meta: { title: '编辑学院', noCache: true },
        hidden: true
      }
    ]
  },
  
  //专业管理
  {
    path: '/major',
    component: Layout,
    redirect: '/major/table',
    name: '专业管理',
    meta: { title: '专业管理', icon: 'zhuanye' },
    children: [
      {
        path: 'table',
        name: '专业列表',
        component: () => import('@/views/edu/major/list'),
        meta: { title: '专业列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加专业',  
        component: () => import('@/views/edu/major/save'),
        meta: { title: '添加专业', icon: 'edit' }
      },
      {
        path: 'edit/:id',   
        name: 'EduMajorEdit',
        component: () => import('@/views/edu/major/save'),
        meta: { title: '编辑专业', noCache: true },
        hidden: true
      }
    ]
  },
  //班级管理
  {
    path: '/myclass',
    component: Layout,
    redirect: '/myclass/table',
    name: '班级管理',
    meta: { title: '班级管理', icon: 'banji' },
    children: [
      {
        path: 'table',
        name: '班级列表',
        component: () => import('@/views/edu/myclass/list'),
        meta: { title: '班级列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加班级',  
        component: () => import('@/views/edu/myclass/save'),
        meta: { title: '添加班级', icon: 'edit' }
      },
      {
        path: 'edit/:id',   
        name: 'EduClassEdit',
        component: () => import('@/views/edu/myclass/save'),
        meta: { title: '编辑班级', noCache: true },
        hidden: true
      }
    ]
  },

  //教师管理
  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/table',
    name: '教师管理',
    meta: { title: '教师管理', icon: 'peoples' },
    children: [
      {
        path: 'table',
        name: '教师列表',
        component: () => import('@/views/edu/teacher/list'),
        meta: { title: '教师列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加教师',  
        component: () => import('@/views/edu/teacher/save'),
        meta: { title: '添加教师', icon: 'edit' }
      },
      {
        path: 'edit/:id',   
        name: 'EduTeacherEdit',
        component: () => import('@/views/edu/teacher/save'),
        meta: { title: '编辑教师', noCache: true },
        hidden: true
      }
    ]
  },

  //学生管理
  {
    path: '/student',
    component: Layout,
    redirect: '/student/table',
    name: '学生管理',
    meta: { title: '学生管理', icon: 'peoples'},
    children: [
      {
        path: 'table',
        name: '学生列表',
        component: () => import('@/views/edu/student/list'),
        meta: { title: '学生列表', icon: 'list' }
      },
      {
        path: 'save',
        name: '添加学生',  
        component: () => import('@/views/edu/student/save'),
        meta: { title: '添加学生', icon: 'edit' }
      },
      {
        path: 'edit/:id',   
        name: 'EduStudentEdit',
        component: () => import('@/views/edu/student/save'),
        meta: { title: '编辑学生', noCache: true },
        hidden: true
      }
    ]
  },


  {
    path: '/temp',
    component: Layout,
    redirect: '/temp/studenttable',
    name: '温度监控',
    meta: { title: '温度管理', icon: 'dashboard'},
    children: [
      {
        path: 'studenttable',
        name: '学生温度监控',
        component: () => import('@/views/edu/temp/studentlist'),
        meta: { title: '学生温度监控', icon: 'user' }
      },
      {
        path: 'teachertable',
        name: '教师温度监控',
        component: () => import('@/views/edu/temp/teacherlist'),
        meta: { title: '教师温度监控', icon: 'user' }
      },
      {
        path: 'save',
        name: '设备管理',  
        component: () => import('@/views/edu/temp/save'),
        meta: { title: '设备管理', icon: 'chart' }
      },
      {
        path: 'show/:id',   
        name: 'showtemp',
        component: () => import('@/views/edu/temp/showtempinfo'),
        meta: { title: '温度详细信息', noCache: true },
        hidden: true
      },
      {
        path: 'edit/:id',   
        name: 'EduTempEdit',
        component: () => import('@/views/edu/temp/save'),
        meta: { title: '编辑温度', noCache: true },
        hidden: true
      }
    ]
  },

  {
    path: '/trip',
    component: Layout,
    redirect: '/trip/studenttable',
    name: '行程监控',
    meta: { title: '行程管理', icon: 'guide'},
    children: [
      {
        path: 'studenttable',
        name: '学生行程监控',
        component: () => import('@/views/edu/trip/studentlist'),
        meta: { title: '学生行程监控', icon: 'user' }
      },
      {
        path: 'teachertable',
        name: '教师行程监控',
        component: () => import('@/views/edu/trip/teacherlist'),
        meta: { title: '教师行程监控', icon: 'user' }
      },
      {
        path: 'yiqing',
        name: '行程记录管理',  
        component: () => import('@/views/edu/trip/yiqing'),
        meta: { title: '行程记录管理', icon: 'chart' }
      },
      {
        path: 'map',
        name: '疫情地图',  
        component: () => import('@/views/edu/trip/map'),
        meta: { title: '疫情地图', icon: 'chart' }
      },
      {
        path: 'show/:id',   
        name: 'showtrip',
        component: () => import('@/views/edu/trip/showtripinfo'),
        meta: { title: '行程详细信息', noCache: true },
        hidden: true
      },
    ]
  },
//文档接口
  {
    path: '/monitored',
    component: Layout,
    redirect: '/monitored/swagger-ui8011',
    name: '文档接口',
    meta: { title: '文档接口', icon: 'clipboard'},
    children: [
      {
        path: 'swagger8011',
        name: 'main文档接口',
        component: () => import('@/views/edu/monitored/swagger-ui8011'),
        meta: { title: 'main文档接口', icon: 'excel' }
      },
      {
        path: 'swagger8012',
        name: 'oss文档接口',
        component: () => import('@/views/edu/monitored/swagger-ui8012'),
        meta: { title: 'oss文档接口', icon: 'excel' }
      },
      {
        path: 'swagger8013',
        name: 'msm文档接口',
        component: () => import('@/views/edu/monitored/swagger-ui8013'),
        meta: { title: 'msm文档接口', icon: 'excel' }
      },
      {
        path: 'swagger8014',
        name: 'backend文档接口',
        component: () => import('@/views/edu/monitored/swagger-ui8014'),
        meta: { title: 'backend文档接口', icon: 'excel' }
      },
    ]
  },

  
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
