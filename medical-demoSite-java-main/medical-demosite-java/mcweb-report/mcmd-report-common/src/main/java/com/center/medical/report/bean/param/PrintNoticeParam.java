package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PrintNoticeParam extends BaseParam implements Serializable {

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "打印类型1：复查；2：职业禁忌证；3：可疑职业病")
    private String idPatientclass;

    @ApiModelProperty(value = "团体名称cid")
    private String khdwmcid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "总结分类ID")
    private String serialNo;



    @ApiModelProperty(value = "选中的体检号(打印使用)")
    private List<String> rowsId;

    @ApiModelProperty(value = "打印的份数(打印使用)")
    private String amount;


}
