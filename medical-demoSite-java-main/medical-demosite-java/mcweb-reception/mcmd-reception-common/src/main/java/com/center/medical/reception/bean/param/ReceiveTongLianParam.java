package com.center.medical.reception.bean.param;

import com.center.medical.bean.enums.Kkfs;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通联支付退款参数
 */
@Data
public class ReceiveTongLianParam implements Serializable {
    private static final long serialVersionUID = 7092994543682423540L;

    @ApiModelProperty(value = "版本号")
    private Long version;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "消费数据")
    private TJKDataParam data;

    @ApiModelProperty(value = "支付记录id")
    private String id;

    /**
     * @see Kkfs
     */
    @ApiModelProperty(value = "扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分 8.微信扫码支付 9.通联支付 10.随行付", position = 9, required = true)
    private Integer kkfs;

}
