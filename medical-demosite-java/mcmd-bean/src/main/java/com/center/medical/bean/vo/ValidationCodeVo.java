package com.center.medical.bean.vo;

import com.center.medical.bean.dto.ShareReportDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 校验验证码并返回列表数据 返回数据
 */
@Data
public class ValidationCodeVo implements Serializable {
    private static final long serialVersionUID = 3918811070486923652L;

    @ApiModelProperty(value = "返回列表数据 ")
    private List<ShareReportDto> list;

    @ApiModelProperty(value = "链接名称")
    private String pathName;

    @ApiModelProperty(value = "已选人数")
    private Integer num;

    @ApiModelProperty(value = "有效期 7,14,30,999")
    private Integer expirationDate;

}
