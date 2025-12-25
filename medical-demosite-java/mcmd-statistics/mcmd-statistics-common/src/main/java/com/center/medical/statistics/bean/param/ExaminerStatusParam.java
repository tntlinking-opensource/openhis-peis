package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  体检状态统计 分页参数
 */
@Data
public class ExaminerStatusParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5608000192885762091L;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;
}
