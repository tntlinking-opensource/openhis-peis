package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC职业病检查结论(ZySummary)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:19
 */
@Data
@TableName("md_zy_summary")
@ApiModel(value = "ZySummary", description = "JC职业病检查结论实体类")
public class ZySummary extends Model<ZySummary> implements Serializable {
    private static final long serialVersionUID = -56804773361037114L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "序列号")
    private Integer serialNo;

    @ApiModelProperty(value = "结论代码")
    private String occupationSummaryCode;

    @ApiModelProperty(value = "结论")
    private String occupationSummary;

    @ApiModelProperty(value = "结论简称")
    private String printForShort;

    @ApiModelProperty(value = "备注")
    private String occupationSummaryExplain;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
