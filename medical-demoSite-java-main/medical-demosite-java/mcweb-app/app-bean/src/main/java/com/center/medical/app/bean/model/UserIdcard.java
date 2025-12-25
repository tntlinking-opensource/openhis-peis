package com.center.medical.app.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户和身份证关联表(UserIdcard)表实体类
 *
 * @author ay
 * @since 2023-08-23 15:12:06
 */
@Data
@TableName("md_user_idcard")
@ApiModel(value = "UserIdcard", description = "用户和身份证关联表实体类")
public class UserIdcard extends Model<UserIdcard> implements Serializable {
    private static final long serialVersionUID = -53203448860995434L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "本系统userId")
    private String userId;


    @ApiModelProperty(value = "身份证号")
    private String idcard;


    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public UserIdcard(String userId, String idcard, Date createTime, Date updateTime) {
        this.userId = userId;
        this.idcard = idcard;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public UserIdcard() {
    }
}
