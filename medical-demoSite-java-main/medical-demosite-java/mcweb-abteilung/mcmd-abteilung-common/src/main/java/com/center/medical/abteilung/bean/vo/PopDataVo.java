package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取自助项目弹窗数据,某些需要客户自己测的项目需要登记完成检测或弃检
 */
@Data
public class PopDataVo implements Serializable {
    private static final long serialVersionUID = 6353855734561314233L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称:必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”")
    private String examfeeitemName;


}
