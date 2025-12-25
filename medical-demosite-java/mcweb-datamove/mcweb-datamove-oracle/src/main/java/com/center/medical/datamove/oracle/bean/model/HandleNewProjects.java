package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * KS检验科加项处理(HandleNewProjects)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:09
 */
@Data
@TableName("HANDLE_NEW_PROJECTS")
@ApiModel(value = "HandleNewProjects", description = "KS检验科加项处理实体类")
public class HandleNewProjects extends Model<HandleNewProjects> implements Serializable {
    private static final long serialVersionUID = -12047314772291729L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "项目ID")
    private String projectsId;

    @ApiModelProperty(value = "加项医生")
    private String addDoctorId;

    @ApiModelProperty(value = "是否已处理")
    private Integer isHandle;

    @ApiModelProperty(value = "处理时间")
    private Date handleTime;

    @ApiModelProperty(value = "处理人")
    private String handleNameId;

    @ApiModelProperty(value = "处理状态")
    private Integer status;

    @ApiModelProperty(value = "处理意见")
    private String idea;

    @ApiModelProperty(value = "0:不删除 1：删除")
    private Integer isDelete;

    @ApiModelProperty(value = "类型0加项1迟检2补检")
    private Integer handleType;
}
