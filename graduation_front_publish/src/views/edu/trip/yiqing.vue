<template>
  <div class="app-container">
    <div style="width: 400px; height: 50px">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-button
            type="success"
            round
            icon="el-icon-star-off"
            @click="getRomdomTrip()"
            >生成随机行程</el-button
          >
        </el-form-item>
        <el-form-item label="监听设备上传">
          <el-switch
            v-model="flagstatic"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="Listentrip()"
          >
          </el-switch>
        </el-form-item>
      </el-form>
    </div>>
    <!-- <div id="allMap" style="width:1000px; height:650px;magin-left:100px; float: center"></div> -->
    <br>
    <div id="allpeople" style="width:750px; height: 500px; float: left"></div>
    <div id="registerInit" style="width:650px; height: 500px; float: right"></div>
    <br>

    <!-- 隐藏router -->
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
import collegeApi from "@/api/edu/college";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";
import tripApi from "@/api/edu/trip";
//导入疫情后台服务
import yiqingApi from "@/api/edu/yiqing";
//导入行程
import { regionData, CodeToText } from "element-china-area-data";
//使用echars
import echarts from "echarts";

//导入地图
require("echarts/map/js/china");

export default {
  name: "",
  inject:['reload'],
  components: { ImageCropper, PanThumb },
  data() {
    return {
      flagstatic: false, //默认关闭监听
      allCollege: [], //所有学院信息
      collegenames: [], //各个学院名称
      allpeopelchart: {}, //echars初始化对象
      mapchart: {},
      psum: 0, //总人数
      peopelarr: [], //各个学院更新行程信息人数
      options: regionData,
      selectedOptions: [],
      addtions: {}, //存储地址数据
      provinceListAll: [], // 全国34个省份（包括直辖市）列表
      cityListAll: [], // 全国各个城市列表
      citySelectAble: false, // 城市是否可选
      dataList: [], //疫情地图数据
      provinces: [], //所有城市疫情信息,
      selecttype: "confirmed",
      mapoption:[],
      subFunc:[], //截止日期
      };
  },
  created() {

  },
  //监视
  watch:{
    dataList: {
　　　　handler(newValue, oldValue) {
          this.mapInit()
　　　　},
　　　　deep: true
　　}
  },
  methods: {
    //监听设备
    Listentrip() {
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
    //初始化
    init() {
       //this.mapdata();//地图初始化
       this.getRefresh();
       this.getAllPeopelFunc();
    },

    //请求后端人员列表
    getAllPeopelFunc() {
      collegeApi.getAllPeopel().then((response) => {
        this.allCollege = response.data.allCollege;
        this.collegenames = response.data.collegenames;
        this.psum = response.data.psum;
        console.log(this.allCollege);
        console.log(this.collegenames);
        this.AllpeopleInit(); //饼图初始化
        this.registerInit(); //柱状图初始化
      });
    },
    //获取每个学院今日更新人数信息
    getRefresh() {
      tripApi.getRefreshtrip().then((response) => {
        this.peopelarr = response.data.refreshTrip;
        console.log(this.peopelarr);
      });
    },
    //人数分布图初始化
    AllpeopleInit() {
      this.allpeopelchart = echarts.init(document.getElementById("allpeople"));
      var seriesData = [];
      for (var i = 0; i < this.allCollege.length; i++) {
        seriesData.push({
          value: this.allCollege[i].sum,
          name: this.allCollege[i].name,
        });
      }

      var legendData = this.collegenames;
      var colorList = [
        "#73DDFF",
        "#73ACFF",
        "#FDD56A",
        "#FDB36A",
        "#FD866A",
        "#9E87FF",
        "#58D5FF",
      ];
      this.peopeloption = {
        backgroundColor: {
          type: "linear",
          x: 0,
          y: 0,
          x2: 1,
          y2: 1,
          colorStops: [
            {
              offset: 0,
              color: "#0f2c70", // 0% 处的颜色
            },
            {
              offset: 1,
              color: "#091732", // 100% 处的颜色
            },
          ],
          globalCoord: false, // 缺省为 false
        },
        title: {
          text: "百院:" + this.psum + "人",
          x: "center",
          y: "center",
          textStyle: {
            color: "#fff",
          },
        },
        tooltip: {
          trigger: "item",
          borderColor: "rgba(255,255,255,.3)",
          backgroundColor: "rgba(13,5,30,.6)",
          borderWidth: 1,
          padding: 5,
          formatter: function (parms) {
            var str =
              parms.marker +
              "" +
              parms.data.name +
              "</br>" +
              "数量：" +
              parms.data.value +
              "人</br>" +
              "占比：" +
              parms.percent +
              "%";
            return str;
          },
        },
        legend: {
          type: "scroll",
          orient: "vertical",
          left: "left",
          align: "auto",
          top: "middle",
          textStyle: {
            color: "#fff",
          },
          data: legendData,
        },
        series: [
          {
            type: "pie",
            z: 3,
            center: ["50%", "50%"],
            radius: ["25%", "45%"],
            clockwise: true,
            avoidLabelOverlap: true,
            hoverOffset: 15,
            itemStyle: {
              normal: {
                color: function (params) {
                  return colorList[params.dataIndex];
                },
              },
            },
            label: {
              show: true,
              position: "outside",
              formatter: "{a|{b}：{d}%}\n{hr|}",
              rich: {
                hr: {
                  backgroundColor: "t",
                  borderRadius: 3,
                  width: 3,
                  height: 3,
                  padding: [3, 3, 0, -12],
                },
                a: {
                  padding: [-30, 15, -20, 15],
                },
              },
            },
            labelLine: {
              normal: {
                length: 20,
                length2: 30,
                lineStyle: {
                  width: 1,
                },
              },
            },
            data: seriesData,
          },
          {
            name: "第一层环",
            type: "pie",
            z: 2,
            tooltip: {
              show: false,
            },
            center: ["50%", "50%"],
            radius: ["45%", "58%"],
            hoverAnimation: false,
            clockWise: false,
            itemStyle: {
              normal: {
                // shadowBlur: 40,
                // shadowColor: 'rgba(0, 255, 255,.3)',
                color: "rgba(1,15,80,.4)",
              },
              emphasis: {
                color: "rgba(1,15,80,.4)",
              },
            },
            label: {
              show: false,
            },
            data: [100],
          },
          {
            name: "第二层环",
            type: "pie",
            z: 1,
            tooltip: {
              show: false,
            },
            center: ["50%", "50%"],
            radius: ["58%", "76%"],
            hoverAnimation: false,
            clockWise: false,
            itemStyle: {
              normal: {
                // shadowBlur: 40,
                // shadowColor: 'rgba(0, 255, 255,.3)',
                color: "rgba(0,15,69,.2)",
              },
              emphasis: {
                color: "rgba(0,15,69,.2)",
              },
            },
            label: {
              show: false,
            },
            data: [100],
          },
        ],
      };
      this.allpeopelchart.setOption(this.peopeloption);
    },
    //截取某个字符前后函数
    zifu(str, mubiao, state) {
      var index = str.lastIndexOf(mubiao);
      if (state == 0) {
        str = str.substring(0, index);
      } else {
        str = str.substring(index + 1, str.length);
      }
      return str;
    },
    //行程登记人数记录初始化
    registerInit() {
      this.registerelchart = echarts.init(
        document.getElementById("registerInit")
      );
      var Xname = [];
      var Yname1 = []; //总人数
      var Yname2 = []; //更新人数
      for (var i = 0; i < this.collegenames.length; i++) {
        Xname.push(this.zifu(this.collegenames[i], "学院", 0));
      }
      for (var i = 0; i < this.allCollege.length; i++) {
        Yname1.push(this.allCollege[i].sum);
      }
      //每个学院的更新人数
      for (var i = 0; i < this.peopelarr.length; i++) {
        Yname2.push(this.peopelarr[i].sum);
      }
      console.log("Yname2:" + Yname2);
      this.registeroption = {
        title: {
          text: "今日行程登记人数对比",
          top: 10,
          left: 10,
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c}",
        },
        toolbox: {
          show: true,
          top: 10,
          right: 10,
          feature: {
            mark: { show: true },
            magicType: { show: true, type: ["line", "bar"] },
            restore: { show: true },
            saveAsImage: { show: true },
          },
        },
        grid: {
          top: 60,
          right: 70,
          bottom: 30,
          left: 60,
        },
        legend: {
          top: 32,
          left: "center",
          data: ["学院总人数", "更新人数"],
        },
        calculable: true,
        xAxis: [
          {
            type: "category",
            data: Xname,
            nameTextStyle: {
              fontSize: 12,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "学\n院\n总\n人\n数\n",
            nameLocation: "center",
            nameGap: 35,
            nameRotate: 0,
            nameTextStyle: {
              fontSize: 14,
            },
            min: 0,
            max: this.psum, //最大人数
            //默认以千分位显示，不想用的可以在这加一段
            axisLabel: {
              //调整左侧Y轴刻度， 直接按对应数据显示
              show: true,
              showMinLabel: true,
              showMaxLabel: true,
              formatter: function (value) {
                return value;
              },
            },
          },
          {
            type: "value",
            name: "已\n更\n新\n行\n程\n人\n数",
            nameLocation: "center",
            nameGap: 50,
            nameRotate: 0,
            nameTextStyle: {
              fontSize: 14,
            },
            min: 0,
            max: this.psum, //最大人数
            //默认以千分位显示，不想用的可以在这加一段
            axisLabel: {
              //调整左侧Y轴刻度， 直接按对应数据显示
              show: true,
              showMinLabel: true,
              showMaxLabel: true,
              formatter: function (value) {
                return value;
              },
            },
          },
        ],

        series: [
          {
            name: "学院总人数",
            type: "bar",
            yAxisIndex: 0,
            data: Yname1,
            itemStyle: {
              normal: { label: { show: true } },
              barBorderRadius: [20],
            },
            // markPoint : {
            //     data : [
            //         {type : 'max', name: '最大值'},
            //         {type : 'min', name: '最小值'}
            //     ]
            // }
          },
          {
            name: "更新人数",
            type: "bar",
            yAxisIndex: 1,
            data: Yname2,
            itemStyle: {
              normal: { label: { show: true } },
              barBorderRadius: [20],
            },
            // markPoint : {
            //     data : [
            //         {type : 'max', name: '最大值'},
            //         {type : 'min', name: '最小值'}
            //     ]
            // }
          },
        ],
      };
      this.registerelchart.setOption(this.registeroption);
    },
    //生成随机测试行程
    getRomdomTrip() {
      tripApi.getRandomtrip().then((response) => {
        //提示信息
        this.$message({
          type: "success",
          message: "添加测试数据成功!",
        });
        this.reload ();
      });
    },
    //疫情地图显示
    mapdata() {
      //获取全国数据
      yiqingApi
        .getNationalProvinces(this.selecttype)
        .then((response) => {
          this.provinces = response.data.provinceMapVO.provinces;
          this.dataList = this.provinces
        })
        .catch(() => {
          console.log("获取数据失败");
        });
       this.mapchart = echarts.init(document.getElementById("allMap"))
  
    },
    mapInit(){
      const mydate = new Date();
        this.mapoption = ({
          tooltip: {
          triggerOn: "click",
          formatter: function (e, t, n) {
            return ".5" == e.value
              ? e.name + "：有疑似病例"
              : e.seriesName + "<br />" + e.name + "：" + e.value;
          },
        },
       title: {
        text: '疫情病例趋势图',
        textStyle: {
            color: '#000',
            fontSize: 18
        },
        subtext: "截止日期:"+mydate,
        subtextStyle: {
            color: '#333',
            fontSize: 14,
            rich: {
                a: {
                    color: '#b61e33',
                    fontSize: 15
                },
                b: {
                    color: '#ff6600',
                    fontSize: 15
                },
                c: {
                    color: 'rgb(17, 191, 199)',
                    fontSize: 15
                },
                d: {
                    color: 'gray',
                    fontSize: 15
                }
            }
        },
        itemGap: 5,
        x: 'center'
      },
        toolbox: {
          show: true,
          orient: "vertical",
          left: "right",
          top: "center",
          feature: {
            dataView: { readOnly: false },
            restore: {},
            saveAsImage: {},
          },
        }, // 提供下载工具
        visualMap: {
          min: 0,
          max: 100000,
          left: 26,
          bottom: 40,
          showLabel: !0,
          text: ["高", "低"],
          pieces: [
            {
              gt: 10000,
              label: "> 10000人",
              color: "#7f1100",
            },
            {
              gte: 1000,
              lte: 10000,
              label: "1000 - 10000人",
              color: "#ff5428",
            },
            {
              gte: 100,
              lt: 1000,
              label: "100 - 1000人",
              color: "#ff8c71",
            },
            {
              gt: 10,
              lt: 100,
              label: "10 - 100人",
              color: "#ffd768",
            },
            {
              gt: 1,
              lt: 10,
              label: "1 - 10人",
              color: "#ffffff",
            },
          ],
          show: !0,
        },
        geo: {
          map: "china",
          roam: !1,
          scaleLimit: {
            min: 1,
            max: 2,
          },
          zoom: 1.23,
          top: 120,
          label: {
            normal: {
              show: !0,
              fontSize: "14",
              color: "rgba(0,0,0,0.7)",
            },
          },
          itemStyle: {
            normal: {
              //shadowBlur: 50,
              //shadowColor: 'rgba(0, 0, 0, 0.2)',
              borderColor: "rgba(0, 0, 0, 0.2)",
            },
            emphasis: {
              areaColor: "#f2d5ad",
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              borderWidth: 0,
            },
          },
        },
        series: [
          {
            name: "确诊病例",
            type: "map",
            geoIndex: 0,
            data: this.dataList,
          },
        ],
      })
      this.mapchart.setOption(this.mapoption); 
    },

  },
  mounted() {
    // this.getAllPeopelFunc();
    // this.AllpeopleInit();
    this.init();
  },
};
// mapinit() {
//     // 初始化echarts实例
//     this.chinachart = echarts.init(document.getElementById("china_map"));
//     // 进行相关配置
//     this.chartOption = {
//       tooltip: {
//         // 鼠标移到图里面的浮动提示框
//         // formatter详细配置： https://echarts.baidu.com/option.html#tooltip.formatter
//         formatter(params, ticket, callback) {
//           // params.data 就是series配置项中的data数据遍历
//           let localValue, perf, downloadSpeep, usability, point;
//           if (params.data) {
//             localValue = params.data.value;
//             perf = params.data.perf;
//             downloadSpeep = params.data.downloadSpeep;
//             usability = params.data.usability;
//             point = params.data.point;
//           } else {
//             // 为了防止没有定义数据的时候报错写的
//             localValue = 0;
//             perf = 0;
//             downloadSpeep = 0;
//             usability = 0;
//             point = 0;
//           }
//           let htmlStr = `
//         <div style='font-size:18px;'> ${params.name}</div>
//         <p style='text-align:left;margin-top:-10px;'>
//           区域对应数据value：${localValue}<br/>
//           性能perf：${perf}<br/>
//           下载速度downloadSpeep：${downloadSpeep}<br/>
//           可用性usability：${usability}<br/>
//           监测点数point：${point}<br/>
//         </p>
//       `;
//           return htmlStr;
//         },
//         // backgroundColor:"#ff7f50",//提示标签背景颜色
//         // textStyle:{color:"#fff"} //提示标签字体颜色
//       },
//       // visualMap的详细配置解析：https://echarts.baidu.com/option.html#visualMap
//       visualMap: {
//         // 左下角的颜色区域
//         type: "piecewise", // 定义为分段型 visualMap
//         min: 0,
//         max: 1000,
//         itemWidth: 40,
//         bottom: 60,
//         left: 20,
//         pieces: [
//           // 自定义『分段式视觉映射组件（visualMapPiecewise）』的每一段的范围，以及每一段的文字，以及每一段的特别的样式
//           { gt: 900, lte: 1000, label: "非常好", color: "#6ad86e" }, // (900, 1000]
//           { gt: 500, lte: 900, label: "正常", color: "#9adcfa" }, // (500, 900]
//           { gt: 310, lte: 500, label: "警告", color: "#ffeb3b" }, // (310, 500]
//           { gt: 200, lte: 300, label: "较差", color: "#ff9800" }, // (200, 300]
//           { gt: 10, lte: 200, label: "非常差", color: "orangered" }, // (10, 200]
//           { value: 0, label: "无数据", color: "#999" }, // [0]
//         ],
//       },
//       // geo配置详解： https://echarts.baidu.com/option.html#geo
//       geo: {
//         // 地理坐标系组件用于地图的绘制
//         map: "china", // 表示中国地图
//         // roam: true, // 是否开启鼠标缩放和平移漫游
//         zoom: 1.2, // 当前视角的缩放比例（地图的放大比例）
//         label: {
//           show: true,
//         },
//         itemStyle: {
//           // 地图区域的多边形 图形样式。
//           borderColor: "rgba(0, 0, 0, 0.2)",
//           emphasis: {
//             // 高亮状态下的多边形和标签样式
//             shadowBlur: 20,
//             shadowColor: "rgba(0, 0, 0, 0.5)",
//           },
//         },
//       },
//       series: [
//         {
//           name: "", // 浮动框的标题（上面的formatter自定义了提示框数据，所以这里可不写）
//           type: "map",
//           geoIndex: 0,
//           label: {
//             show: true,
//           },
//           // 这是需要配置地图上的某个地区的数据，根据后台的返回的数据进行拼接（下面是我定义的假数据）
//           data: [
//             {
//               name: "北京",
//               value: 599,
//               perf: "0.501s", // 性能
//               downloadSpeep: "1.221MB/s", // 下载速度
//               usability: "100%", // 可用性
//               point: "250", // 监测点
//             },
//             {
//               name: "上海",
//               value: 142,
//             },
//             {
//               name: "黑龙江",
//               value: 44,
//             },
//             {
//               name: "新疆",
//               value: 999,
//               perf: "0.501s", // 性能
//               downloadSpeep: "1.221MB/s", // 下载速度
//               usability: "100%", // 可用性
//               point: "250", // 监测点
//             },
//             {
//               name: "深圳",
//               value: 92,
//             },
//             {
//               name: "湖北",
//               value: 810,
//             },
//             {
//               name: "四川",
//               value: 453,
//             },
//           ],
//         },
//       ],
//     };
//     // 使用刚指定的配置项和数据显示地图数据
//     this.chinachart.setOption(this.chartOption);
//   },
</script>

  