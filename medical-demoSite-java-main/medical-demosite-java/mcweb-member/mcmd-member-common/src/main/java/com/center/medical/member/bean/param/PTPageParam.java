package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 个检预检回访分页参数
 */
@Data
public class PTPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -1222528017715597278L;

    @ApiModelProperty(value = "体检号")
    private String patientname;

    @ApiModelProperty(value = "是否来检：-1空 0是 1.否  2再通知 ")
    private List<String> isInspect;

    @ApiModelProperty(value = "最小体检金额")
    private Double minmoneyamountpaid;

    @ApiModelProperty(value = "最大体检金额")
    private Double maxmoneyamountpaid;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;


    @ApiModelProperty(value = "开单医生")
    private String idOpendoctor;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心id")
    private String fzxid;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否为空，true是,false否")
    private String hasEmpty;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "is_inspect是否为空，true是,false否")
    private String flag;
}
