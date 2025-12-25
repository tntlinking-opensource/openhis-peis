package com.center.medical.reservation.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团体预约分享码(ReservationGroupCode)表实体类
 *
 * @author ay
 * @since 2024-03-08 16:38:39
 */
@Data
@TableName("md_reservation_group_code")
@ApiModel(value = "ReservationGroupCode", description = "团体预约分享码实体类")
public class ReservationGroupCode extends Model<ReservationGroupCode> implements Serializable {
    private static final long serialVersionUID = -32403043413314494L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "提取码")
    private String extractedCode;


    @ApiModelProperty(value = "分组id")
    private String groupId;


    @ApiModelProperty(value = "团体ID")
    private String idOrg;


    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;


    @ApiModelProperty(value = "有效期,7,14,30,999")
    private Integer expirationDate;


    @ApiModelProperty(value = "过期时间")
    private Date expirationTime;


    @ApiModelProperty(value = "状态 0生效1过期")
    private Integer status;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "创建人id")
    private String createId;


    @ApiModelProperty(value = "修改时间")
    private Date modifydate;


    @ApiModelProperty(value = "修改人id")
    private String modifyId;

}
