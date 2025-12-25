package com.center.medical.abteilung.bean.vo;

import com.center.medical.abteilung.bean.dto.DivisionDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室列表返回数据
 */
@Data
public class AbteilungListVo implements Serializable {
    private static final long serialVersionUID = -2581874537452985071L;

    @ApiModelProperty(value = "PACS的地址")
    private String lisPacsUrl;

    @ApiModelProperty(value = "当前登录的用户名")
    private String userName;

    @ApiModelProperty(value = "PACS打开或关闭，1打开 0关闭")
    private String pacsOpen;

    @ApiModelProperty(value = "科室列表数据")
    private List<DivisionDto> listQD;

    @ApiModelProperty(value = "信息")
    private String msg;

    @ApiModelProperty(value = "是否拥有pacs的权限")
    private String ownResource;

    @ApiModelProperty(value = "体检号")
    private String patientCode;
}
