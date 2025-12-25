package com.center.medical.reception.bean.param;

import com.center.medical.reception.bean.dto.GCSaOrUpDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 团检加/弃项右侧数据保存
 */
@Data
public class GCSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -5627405716662551701L;

    @ApiModelProperty(value = "ids，体检者id")
    private List<String> ids;

    @ApiModelProperty(value = "分组id（老系统的tcId）")
    private String groupid;

    @ApiModelProperty(value = "团检加/弃项保存表格数据")
    private List<GCSaOrUpDto> griddata;
}
