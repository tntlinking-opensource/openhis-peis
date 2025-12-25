package com.center.medical.reception.bean.param;

import com.center.medical.reception.bean.model.JlcGrid;
import com.center.medical.reception.bean.model.TestGrid;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检验科
 */
@Data
public class DivisionInspectionParam implements Serializable {

    private static final long serialVersionUID = -1226573639448669869L;

    @ApiModelProperty(value = "是否补全，true是false否")
    private String autoFill;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "科室ID")
    private String ksId;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "小结")
    private String conclusions;

    @ApiModelProperty(value = "LIS表数据(审核时使用)")
    private List<TestGrid> testGrids;

    @ApiModelProperty(value = "检查结果子表数据(审核时使用)")
    private List<JlcGrid> jlcGrids;


}
