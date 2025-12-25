package com.center.medical.data.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class BaseDictionaryParam extends BaseParam implements Serializable {

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "名字")
    private String mz;

    @ApiModelProperty(value = "分类代码")
    private String classCode;

    @ApiModelProperty(value = "DICTIONARY_NAME名称")
    private String key;

    @ApiModelProperty(value = "id")
    private String id;

}
