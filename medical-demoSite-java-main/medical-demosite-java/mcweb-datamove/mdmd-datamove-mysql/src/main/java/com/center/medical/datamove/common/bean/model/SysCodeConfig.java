package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 加密密钥及授权码表(CodeConfig)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
@Data
@TableName("sys_code_config")
@ApiModel(value = "CodeConfig", description = "加密密钥及授权码表实体类")
public class SysCodeConfig extends Model<SysCodeConfig> implements Serializable {
    private static final long serialVersionUID = -65701535439332026L;

    @TableId(value = "id")
    @ApiModelProperty(value = "记录ID")
    private String id;

    @ApiModelProperty(value = "键，如：使用对象ID（key与bs_flag组成唯一值）")
    private String keyText;

    @ApiModelProperty(value = "值，如：加密密钥/授权码等")
    private String valueText;

    @ApiModelProperty(value = "业务标识")
    private String bsFlag;

    @ApiModelProperty(value = "记录说明")
    private String remark;

    @ApiModelProperty(value = "状态：0.无效 1.正常")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
