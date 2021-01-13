package top.weidaboy.servicemain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author vinda
 * @since 2020-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Temp对象", description="")
public class Temp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "温度ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学院ID")
    private String collegeid;

    @ApiModelProperty(value = "专业ID")
    private String majorid;

    @ApiModelProperty(value = "班级ID")
    private String classid;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "温度信息")
    private String temp;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "1：没有处理  0：已经处理")
    private Integer tempresolve;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "温度修改时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtTempend;


}
