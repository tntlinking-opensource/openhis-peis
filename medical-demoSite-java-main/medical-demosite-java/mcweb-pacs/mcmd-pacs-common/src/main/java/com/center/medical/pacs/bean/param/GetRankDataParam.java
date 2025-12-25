package com.center.medical.pacs.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检者列表数据 请求数据
 */
@Data
public class GetRankDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3045448992462269129L;

    @ApiModelProperty(value = "科室id（不需要传）")
    private String ksID;


    @ApiModelProperty(value = "PACS开启或关闭 1开启0关闭（不需要传）")
    private Integer union;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "0无图 1有图")
    private String jcstatus;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;


}
