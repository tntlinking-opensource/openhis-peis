package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 编辑收费项目-右下表格数据
 */
@Data
public class ListDatasVo implements Serializable {
    private static final long serialVersionUID = -7090008748525691193L;


    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "检查项目ID")
    private String cid;

    @ApiModelProperty(value = "序号")
    private Integer orderIndex;

    @ApiModelProperty(value = "检查项目名称")
    private String name;

    @ApiModelProperty(value = "检查项目类型")
    private String jcxmlx;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer sex;

    @ApiModelProperty(value = "危急值下限")
    private Integer wjzxx;

    @ApiModelProperty(value = "危急值上限")
    private Integer wjzsx;

    @ApiModelProperty(value = "科室名称")
    private String ksmc;

    @ApiModelProperty(value = "体检类型：0:健康检查类型1:职业检查类型2:健康+职业(职业)")
    private Integer lx;

    @ApiModelProperty(value = "接口代码")
    private String interfaceCode;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "男性上限")
    private Integer valuemalemax;

    @ApiModelProperty(value = "男性下限")
    private Integer valuemalemin;

    @ApiModelProperty(value = "女性上限")
    private Integer valuefemalemax;

    @ApiModelProperty(value = "女性下限")
    private Integer valuefemalemin;

    @ApiModelProperty(value = "体征上限")
    private Integer tzsx;

    @ApiModelProperty(value = "体征下限")
    private Integer tzxx;
}
