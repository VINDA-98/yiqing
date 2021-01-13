package top.weidaboy.servicemain.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Student显示对象", description="")
public class StudentVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学生登录账号")
    private String account;

    @ApiModelProperty(value = "班级ID")
    private String classid;

    @ApiModelProperty(value = "班级名称")
    private String classname;

    @ApiModelProperty(value = "专业ID")
    private String majorid;

    @ApiModelProperty(value = "专业名称")
    private String majorname;

    @ApiModelProperty(value = "学院ID")
    private String collegeid;

    @ApiModelProperty(value = "学院名称")
    private String collegename;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "学生性别（1：男，0：女） 默认为1")
    private Integer sex;

    @ApiModelProperty(value = "学生年龄")
    private Integer age;

    @ApiModelProperty(value = "学生手机号码")
    private String phone;

    @ApiModelProperty(value = "学生邮箱")
    private String email;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

}
