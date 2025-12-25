package com.center.medical.data.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-17 10:20
 * @description: 收费项目参数
 */
@Data
public class ItemsParam extends BaseParam implements Serializable {


    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "适用性别：0男性 1女性 2通用，老系统的xb")
    private Integer forMale;

    @ApiModelProperty(value = "输入码,就是老系统的key")
    private String sfxmsrm;

    public String getSfxmsrm() {
        if (StringUtils.isNotEmpty(sfxmsrm)) {
            return sfxmsrm.toUpperCase();
        }
        return null;
    }

    @ApiModelProperty(value = "健康--> 健康+综合，职业、复查--> 职业，综合--> 健康、职业、综合")
    private Integer thiredTj;

    @ApiModelProperty(value = "男--> 男、通用，女--> 女、通用，通用--> 通用")
    private Integer thiredSex;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer tjlx;

    @ApiModelProperty(value = "科室ID")
    private String idDepart;

    @ApiModelProperty(value = "标本类型ID")
    private String idLabtype;

    @ApiModelProperty(value = "接口代码")
    private String examfeeitemCode;

    @ApiModelProperty(value = "是否禁用：0.否 1.是")
    private Integer isBan;

    @ApiModelProperty(value = "是否科室加项：0.否 1.是")
    private Integer addItem;

    @ApiModelProperty(value = "最低价格")
    private Double minPrice;

    @ApiModelProperty(value = "最高价格")
    private Double maxPrice;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否拥有超级管理员权限：0非超级管理员，1是超级管理员")
    private Integer isAdmin = 1;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心ID")
    private String cId;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "unitprice/coopprice")
    private String flag;


    @ApiModelProperty(value = "参数是否是空的")
    private Integer isNull;

    @ApiModelProperty(value = "请求标识")
    private Integer requestFlag;
}
