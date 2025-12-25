package com.center.medical.bean.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TJ团检报告主表(BallCheckReport)表实体类
 *
 * @author fjj
 * @since 2022-11-08 17:51:54
 */
@Data
public class SamplePersonVo extends Model<SamplePersonVo> implements Serializable {
    private static final long serialVersionUID = 714688106206030282L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "组ID")
    private String groupId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团检报告ID")
    private String ballCheckId;

    @ApiModelProperty(value = "已开始体检")
    private String fExamstarted;

    @ApiModelProperty(value = "已完成体检")
    private String fFinalexamed;

    @ApiModelProperty(value = "体检名称")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、\" +\n" +
            "            \"5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private String jktjzt;

    @ApiModelProperty(value = "职业体检状态：0.总检开始 1.总检完成 2.报告已打印 3.报告一审通过 4.报告一审不通过 5.二审通过 \" +\n" +
            "            \"6.二审不通过 7.终审通过 8.终审不通过 9.报告已交接 10.报告已通知 11.报告已领取")
    private String zytjzt;

    @ApiModelProperty(value = "sampleid")
    private String sampleid;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "体检状态")
    private String tjzt;
}
