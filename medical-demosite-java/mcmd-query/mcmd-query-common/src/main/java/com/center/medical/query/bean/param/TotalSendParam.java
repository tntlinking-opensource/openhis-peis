package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送项目查询 分页参数
 */
@Data
public class TotalSendParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2565525261534170745L;

    @ApiModelProperty(value = "外送机构名字,模糊查询")
    private String center;


}
