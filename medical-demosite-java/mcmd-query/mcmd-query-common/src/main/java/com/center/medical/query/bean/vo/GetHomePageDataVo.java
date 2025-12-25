package com.center.medical.query.bean.vo;

import com.center.medical.query.bean.dto.OverViewDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询首页数据
 */
@Data
public class GetHomePageDataVo implements Serializable {
    private static final long serialVersionUID = -1461616795464916942L;

    @ApiModelProperty(value = "体检中心概况")
    private OverViewDto overView;

}
