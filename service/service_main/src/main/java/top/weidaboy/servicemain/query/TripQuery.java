package top.weidaboy.servicemain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "trip查询对象", description = "行程查询对象封装")
@Data
public class TripQuery {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "行程ID")
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userid;

    @ApiModelProperty(value = "行程")
    private String trip;

    @ApiModelProperty(value = "用户名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "用户所在学院ID")
    private String collegeid;

    @ApiModelProperty(value = "用户所在班级ID")
    private String classid;

    @ApiModelProperty(value = "用户所在专业ID")
    private String majorid;

    @ApiModelProperty(value = "查询模式")
    private String mode;

    @ApiModelProperty(value = "查询天数")
    private String day;

    @ApiModelProperty(value = "查询月数")
    private String month;

    @ApiModelProperty(value = "1：没有处理  0：已经处理")
    private Integer tripreslove;

    @ApiModelProperty(value = "温度最后修改时间")
    private String tripend;

    @ApiModelProperty(value = "各个学院更新行程信息人数")
    private int sum;

    @ApiModelProperty(value = "各个学院没有更新行程信息人数")
    private int nosum;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "市区")
    private String city;

    @ApiModelProperty(value = "地区（县城）")
    private String area;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
