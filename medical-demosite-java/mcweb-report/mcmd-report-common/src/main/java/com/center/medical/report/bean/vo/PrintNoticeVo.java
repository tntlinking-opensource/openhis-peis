package com.center.medical.report.bean.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PrintNoticeVo implements Serializable {
    private static final long serialVersionUID = 2315075718620379104L;


    @ApiModelProperty(value = "XID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "复查通知单生成状态：0未生成 1生成中 2已生成")
    private String status;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;
}
