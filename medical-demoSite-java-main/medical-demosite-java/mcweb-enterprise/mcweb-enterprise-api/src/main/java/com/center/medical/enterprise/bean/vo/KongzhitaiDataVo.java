package com.center.medical.enterprise.bean.vo;

import com.center.medical.enterprise.bean.dto.AgeDistributionDto;
import com.center.medical.enterprise.bean.dto.PhysicalDistributionDto;
import com.center.medical.enterprise.bean.dto.staffDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取控制台返回数据
 */
@Data
public class KongzhitaiDataVo implements Serializable {
    private static final long serialVersionUID = -245157090942461214L;

    @ApiModelProperty(value = "年度体检人数")
    private Long yearNum;

    @ApiModelProperty(value = "本月体检人数")
    private Long monthNum;

    @ApiModelProperty(value = "上月体检人数")
    private Long lastMonthNum;

    @ApiModelProperty(value = "人员构成")
    private List<staffDto> sexData;

    @ApiModelProperty(value = "年龄分布-数量")
    private List<String> ageyAxis;

    @ApiModelProperty(value = "年龄分布-年龄")
    private List<String> agexAxis;

    @ApiModelProperty(value = "体检分布情况")
    private List<PhysicalDistributionDto> tableData;

}
