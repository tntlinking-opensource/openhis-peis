package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateorderParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -1433015404167604779L;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "团体id")
    private String orgId;

    @ApiModelProperty(value = "分中心id")
    private String fzxIds;

    @ApiModelProperty(value = "cid")
    private List<String> cId;

    @ApiModelProperty(value = "xsryId")
    private List<String> xsryId;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;



    @ApiParam(hidden = true)
    @ApiModelProperty(value = "材料管理权限")
    private String clsh;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户id")
    private List<String> userCids;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心id")
    private String fzxid;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "开单助理名称")
    private String kdzlName;


    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "团检号码")
    private String tjhm;

    @ApiModelProperty(value = "订单状态")
    private String spzt;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "按订单排序,1是,0否")
    private String sortByOrder;


    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;

}
