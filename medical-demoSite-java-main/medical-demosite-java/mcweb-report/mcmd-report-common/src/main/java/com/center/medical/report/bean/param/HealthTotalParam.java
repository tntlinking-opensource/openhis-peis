package com.center.medical.report.bean.param;

import com.center.medical.bean.enums.Jktjzt;
import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-12-07 18:59
 * @description: 健康总检查询参数
 */
@Data
public class HealthTotalParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8396042232793584096L;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "0职业1健康")
    private Integer isJk;

    @ApiModelProperty(value = "体检阶段：0.未开始总检 1.开始总检 2.总检完成")
    private Integer examstate;

    /**
     * @see Jktjzt
     */
    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer jktjzt;

    /**
     * @see Jktjzt
     */
    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer zytjzt;

    @ApiModelProperty(value = "体检者类型，接口：基础模块->体检者类型->查询全部")
    private Integer idPatientclass;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记开始日期")
    private Date startRegTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记结束日期")
    private Date endRegTime;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "是否补全，true是false否")
    private String autoFill;

    @ApiModelProperty(value = "分检完成开始日期")
    private Date startFjTime;

    @ApiModelProperty(value = "分检完成开始日期")
    private Date endFjTime;
}
