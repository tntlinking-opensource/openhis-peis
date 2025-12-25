package com.center.medical.bean.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SearchCodeVo implements Serializable {
    private static final long serialVersionUID = -7677221555650532232L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "体检状态：-2检验报告,-1报告上传时生成的,0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private Integer status;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "发送方式ID")
    private String grantId;

    @ApiModelProperty(value = "发送方式名称")
    private String methodName;

}
