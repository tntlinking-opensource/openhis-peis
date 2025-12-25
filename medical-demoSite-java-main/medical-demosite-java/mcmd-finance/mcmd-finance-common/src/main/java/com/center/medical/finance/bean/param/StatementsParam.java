package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 当日线下业绩参数
 */
@Data
public class StatementsParam implements Serializable {
    private static final long serialVersionUID = -1901721439177226747L;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "筛选时间")
    private Date time;

    @ApiModelProperty(value = "id集合,多个")
    private List<String> ids;

}
