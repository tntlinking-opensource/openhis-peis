package com.center.medical.pacslis.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * pacs登记信息查询图表
 */
@Data
public class ChartVo implements Serializable {

    private static final long serialVersionUID = 9034148745130521322L;

    @ApiModelProperty(value = "健康")
    private Integer jk;

    @ApiModelProperty(value = "职业")
    private Integer zy;

    @ApiModelProperty(value = "总数")
    private Integer total;


    public ChartVo() {
    }

    public ChartVo(Integer jk, Integer zy, Integer total) {
        this.jk = jk;
        this.zy = zy;
        this.total = total;
    }
}
