package com.center.medical.data.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-17 9:07
 * @description: 检查项目类型参数
 */
@Data
public class ExamItemTypePrama extends BaseParam implements Serializable {

    private static final long serialVersionUID = 9143783618014830530L;

    @ApiModelProperty(value = "检查项目类型名称")
    private String examitemtypeName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if(StringUtils.isNotBlank(inputCode)){
            return inputCode.toUpperCase();
        }
        return null;
    }

}
