package top.weidaboy.servicemain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Class查询对象", description = "班级查询对象封装")
@Data
public class ClassQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学院ID")
    private String id;
    @ApiModelProperty(value = "学院名称")
    private String name;
    @ApiModelProperty(value = "专业ID")
    private String collegeid;
    @ApiModelProperty(value = "学院ID")
    private String majorid;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
