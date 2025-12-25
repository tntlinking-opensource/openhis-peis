package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-12-02 11:57
 * @description: 前台备单查询参数
 */
@Data
public class DbOrderParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 4003003595971739042L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "客户单位ID")
    private String khdwmcid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = " 客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "按订单排序：0.否 1.是")
    private Integer sortByOrder;

    @ApiModelProperty(value = "接单经理")
    private String kdzlName;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否线上系统")
    private Boolean isOnline;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心id")
    private List<String> cids;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户姓名")
    private String userName;


    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;

}
