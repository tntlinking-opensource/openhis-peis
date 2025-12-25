package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知方式（领取方式）表(Notificationmethod)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_notificationmethod")
@ApiModel(value = "Notificationmethod", description = "通知方式（领取方式）表实体类")
public class Notificationmethod extends Model<Notificationmethod> implements Serializable {
    private static final long serialVersionUID = 102826626842767739L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "通知方式")
    private String methodName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否发送通知：0.否 1.是")
    private String isSendNotice;
}
