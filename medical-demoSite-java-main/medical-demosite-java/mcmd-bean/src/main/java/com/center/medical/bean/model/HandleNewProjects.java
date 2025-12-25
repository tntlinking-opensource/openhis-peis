package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.NewProjectHandleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS检验科加项处理(HandleNewProjects)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_handle_new_projects")
@ApiModel(value = "HandleNewProjects", description = "KS检验科加项处理实体类")
public class HandleNewProjects extends Model<HandleNewProjects> implements Serializable {
    private static final long serialVersionUID = -17431722354363565L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "项目ID")
    private String projectsId;

    @ApiModelProperty(value = "加项医生")
    private String addDoctorId;

    @ApiModelProperty(value = "是否已处理：0.未处理 1.已处理")
    private Integer isHandle;

    @ApiModelProperty(value = "处理时间")
    private Date handleTime;

    @ApiModelProperty(value = "处理人")
    private String handleNameId;

    @ApiModelProperty(value = "处理状态")
    private Integer status;

    @ApiModelProperty(value = "处理意见")
    private String idea;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    /**
     * 加项处理类型：0.加项(含检验科普通增加) 1.弃检 2.补检 3.迟检（检验科） 4.退项（检验科）
     *
     * @see NewProjectHandleType
     */
    @ApiModelProperty(value = "加项处理类型，详见：com.center.medical.bean.enums.NewProjectHandleType")
    private Integer handleType;


    @ApiModelProperty(value = "项目名称")
    @TableField(exist = false)
    private String examfeeitemName;

    @ApiModelProperty(value = "处理人")
    @TableField(exist = false)
    private String handleName;

}
