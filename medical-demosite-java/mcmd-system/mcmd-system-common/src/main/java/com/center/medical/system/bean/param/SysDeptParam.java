package com.center.medical.system.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-17 19:57
 * @description: 查询部门参数
 */
@Data
public class SysDeptParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8923542548564932616L;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "科室号")
    private String ksh;

    @ApiModelProperty(value = "创建时间排序")
    private String cjsjpx;

}
