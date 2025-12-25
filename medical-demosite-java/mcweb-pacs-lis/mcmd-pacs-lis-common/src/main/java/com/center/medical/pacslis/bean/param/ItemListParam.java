package com.center.medical.pacslis.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * PACS登记信息查询参数
 */
@Data
public class ItemListParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -7424140447835851435L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名或输入码")
    private String inputCode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "团体名称")
    private String idOrg;


    public String getPatientcode() {
        if (StringUtils.isEmpty(patientcode)) {
            return null;
        }
        return patientcode.trim().toUpperCase();
    }

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.trim().toUpperCase();
    }
}
