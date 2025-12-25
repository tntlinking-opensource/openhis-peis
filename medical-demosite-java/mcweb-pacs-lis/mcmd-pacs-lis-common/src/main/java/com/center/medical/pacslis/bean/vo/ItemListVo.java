package com.center.medical.pacslis.bean.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * pacs登记信息查询分页数据
 */
@Data
public class ItemListVo implements Serializable {
    private static final long serialVersionUID = -2086930294566966320L;

    @ApiModelProperty(value = "分页数据")
    private IPage<PacsPeispatient> page;


    @ApiModelProperty(value = "图表数据")
    private ChartVo chart;
}
