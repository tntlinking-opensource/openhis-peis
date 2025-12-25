package com.center.medical.report.bean.vo;

import com.center.medical.bean.enums.Jktjzt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-11-23 17:25
 * @description: 检报告待领提醒数据
 */
@Data
public class ReportRemindVo implements Serializable {

    private static final long serialVersionUID = 7591856622018599620L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;

    @ApiModelProperty(value = "交出时间")
    private Date joinTime;

    /**
     * @see Jktjzt
     */
    @ApiModelProperty(value = "体检状态：-2检验报告,-1报告上传时生成的,0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private Integer status;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;


    @ApiModelProperty(value = "柜子号")
    private String chestNum;
}
