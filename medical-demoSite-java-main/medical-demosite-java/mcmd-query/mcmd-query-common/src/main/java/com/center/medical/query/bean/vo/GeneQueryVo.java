package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新产品数据查询 返回数据
 */
@Data
public class GeneQueryVo implements Serializable {
    private static final long serialVersionUID = -7619427855323724968L;

    @Excel(name = "序列")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "团体id")
    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @Excel(name = "单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String orgName;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @Excel(name = "优惠价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实付价格")
    private Double factprice;

    @Excel(name = "项目名称")
    @ApiModelProperty(value = "检查项目打印名称")
    private String itemName;

    @Excel(name = "团检/个检")
    @ApiModelProperty(value = "类型，团体或个人")
    private String type;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "是否加项" ,readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "是否加项,0=否,1=是")
    private String isAdd;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;


    @Excel(name="登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;
}
