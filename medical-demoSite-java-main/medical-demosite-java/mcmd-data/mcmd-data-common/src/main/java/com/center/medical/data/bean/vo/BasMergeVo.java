package com.center.medical.data.bean.vo;

import com.center.medical.data.bean.model.BasMerge;
import com.center.medical.data.bean.model.Basconclusion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BasMergeVo implements Serializable {

    @ApiModelProperty(value = "合并结伦词实体类")
    private BasMerge basMerge;


    @ApiModelProperty(value = "总检结论词实体类list")
    private List<Basconclusion> basconclusionList;
}
