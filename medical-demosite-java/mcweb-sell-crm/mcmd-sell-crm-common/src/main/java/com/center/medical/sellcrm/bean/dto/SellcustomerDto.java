package com.center.medical.sellcrm.bean.dto;

import com.center.medical.common.annotation.Excel;
import com.center.medical.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-02-24 11:21
 * @description: 我的客户导入数据接收实体类
 */
@Data
@ApiModel(value = "SellcustomerDto", description = "我的客户导入数据接收实体类")
public class SellcustomerDto implements Serializable {

    private static final long serialVersionUID = 4482993152908014298L;

    @ApiModelProperty(value = "客户单位名称")
    @Xss(message = "用户账号不能包含脚本字符")
    @Excel(name = "客户单位名称", type = Excel.Type.IMPORT)
    private String khdwmc;

    @ApiModelProperty(value = "客户单位联系人")
    @Xss(message = "用户账号不能包含脚本字符")
    @Excel(name = "客户单位联系人", type = Excel.Type.IMPORT)
    private String khdwlxr;

    @ApiModelProperty(value = "客户电话")
    @Excel(name = "客户电话", type = Excel.Type.IMPORT)
    private String khdh;

    @ApiModelProperty(value = "邮政编码")
    @Excel(name = "邮政编码", type = Excel.Type.IMPORT)
    private String yzbm;

    @ApiModelProperty(value = "客户单位注册地址")
    @Xss(message = "用户账号不能包含脚本字符")
    @Excel(name = "客户单位注册地址", type = Excel.Type.IMPORT)
    private String khdwzcdz;

    @ApiModelProperty(value = "单位机构代码")
    @Xss(message = "用户账号不能包含脚本字符")
    @Excel(name = "单位机构代码", type = Excel.Type.IMPORT)
    private String dwjgdm;
}
