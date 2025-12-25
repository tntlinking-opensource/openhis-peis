package com.center.medical.system.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 金蝶销售员(Saleer)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:11
 */
@Data
@TableName("kd_saleer")
@ApiModel(value = "Saleer", description = "金蝶销售员实体类")
public class KdSaleer extends Model<KdSaleer> implements Serializable {
    private static final long serialVersionUID = 149429895817818634L;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "账户号")
    private String accountNo;

    @ApiModelProperty(value = "状态")
    private String useStatusId;

    @ApiModelProperty(value = "ct_date")
    private Date ctDate;

    @ApiModelProperty(value = "lt_date")
    private Date ltDate;

    @ApiModelProperty(value = "centerorgname")
    private String centerorgname;

    @ApiModelProperty(value = "md5")
    private String md5;

    @TableField(exist = false)
    @ApiModelProperty(value = "状态名称")
    private String useStatusName;
}
