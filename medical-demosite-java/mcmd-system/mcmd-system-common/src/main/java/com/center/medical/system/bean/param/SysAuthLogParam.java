package com.center.medical.system.bean.param;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 生成授权信息参数
 */
@Data
public class SysAuthLogParam {

    @ApiModelProperty(value = "机构ID，对应sys_business_source中的source_id", required = true)
    private String institutionId;

    @ApiModelProperty("机构名称")
    private String institutionName;

    @ApiModelProperty(value = "授权类型：0.永久授权 1.有期限收取", required = true)
    private Integer authType;

    @ApiModelProperty(value = "授权码，对应sys_code_config中的auth_code", required = true)
    private String authCode;

    @ApiModelProperty(value = "授权备注")
    private String remark;

    @ApiModelProperty(value = "开始日期", required = true)
    private Date startTime;

    @ApiModelProperty(value = "到期时间", required = true)
    private Date expiredTime;

    @ApiModelProperty(value = "授权版本号")
    private String version;

    @ApiModelProperty(value = "到期是否可用：0.不可用 1.可用", required = true)
    private Integer canUse;

    @ApiModelProperty(value = "状态：-1已过期 0.失效 1.正常")
    private Integer status;
}
