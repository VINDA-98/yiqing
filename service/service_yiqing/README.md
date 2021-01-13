

这是面向开发者的说明：

准备工作
- 创建mysql数据库，命名为infectstatisticwebdb
- 在创建的数据库中运行src\main\resources\sql下的.sql文件
- 将backend文件夹作为idea工程打开
- 将src\main\resources\application.yml中的数据库账号密码改为你本地的

运行
- 右键src\main\java\com\infect\backend\BackendApplication.java运行
- 在浏览器输入localhost:8014/service/yiqing/hello，若返回一个json格式的helloworld数据，则说明运行成功

接口说明
- 全国当日数据：localhost:8014/serviceyiqing/nations/all
- 各省当日累计确诊：localhost:8014/serviceyiqing/statistics/provinces/confirmed 
- 各省当日现有确诊：localhost:8014/serviceyiqing/statistics/provinces/currentconfirmed
- 某省当日疫情数据：localhost:8014/serviceyiqing/statistics/provinces/one/{省名}
- 某省当日所有城市疫情：localhost:8014/serviceyiqing/cities/list/{省名}
- 某省疫情趋势：localhost:8014/serviceyiqing/statistics/provinces/one/tends/{省名}/{类型}
{类型}参数值：
    - confirmedincr —— 新增确诊趋势 
    - confirmed —— 现有确诊趋势
    - cureddead —— 治愈/死亡趋势
