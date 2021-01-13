package top.weidaboy.servicemain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Student查询对象", description = "學生查询对象封装")
@Data
public class StudentQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "學生学号,模糊查询")
    private String account;
    @ApiModelProperty(value = "學生名称,模糊查询")
    private String name;
    @ApiModelProperty(value = "學生所在学院ID")
    private String collegeid;
    @ApiModelProperty(value = "學生所在班级ID")
    private String classid;
    @ApiModelProperty(value = "學生所在专业ID")
    private String majorid;
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
