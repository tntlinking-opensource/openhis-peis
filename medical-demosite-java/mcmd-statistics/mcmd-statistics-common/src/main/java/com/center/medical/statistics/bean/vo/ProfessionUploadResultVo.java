package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 云平台上传结果分页查询结果
 * @author xhp
 * @since 2023-11-28 9:54
 */
@Data
public class ProfessionUploadResultVo implements Serializable {

    private static final long serialVersionUID = 7310001131042634639L;

    @Excel(name = "体检号" )
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名" )
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "上传状态" ,readConverterExp = "0=未上传,1=上传成功,2=上传失败")
    @ApiModelProperty(value = "上传状态 0未上传 1上传成功 2上传失败")
    private int status;

    @Excel(name = "驳回原因" )
    @ApiModelProperty(value = "驳回原因")
    private String msg;

    @Excel(name = "体检类型",readConverterExp = "1=职业,2=综合,3=复查" )
    @ApiModelProperty(value = "体检类型：1.职业 2.综合 3.复查")
    private String idExamtype;

    @Excel(name = "登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "修改时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @Excel(name = "体检团体" )
    @ApiModelProperty(value = "体检团体")
    private String orgName;

    @Excel(name = "开单医生" )
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

}
