package top.weidaboy.servicemain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Major查询对象", description = "专业查询对象封装")
@Data
public class MajorQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级ID")
    private String id;
    @ApiModelProperty(value = "班级名称")
    private String name;
    @ApiModelProperty(value = "学院id")
    private String collegeid;
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
