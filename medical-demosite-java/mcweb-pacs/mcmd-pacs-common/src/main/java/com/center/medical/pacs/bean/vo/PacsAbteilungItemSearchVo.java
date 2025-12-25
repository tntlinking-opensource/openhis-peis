package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-22 9:40
 */
@Data
public class PacsAbteilungItemSearchVo {
    @ApiModelProperty(value = "图片")
    private List<PacsAbteilungItemSearchImgVo> imgs;
    @ApiModelProperty(value = "历史")
    private PacsAbteilungItemSearchHistoryVo pacsAbteilungItemSearchHistoryVo;
    @ApiModelProperty(value = "小结、描述、检查人等")
    private PacsAbteilungItemSearchMainVo pacsAbteilungItemSearchMainVo;
}
