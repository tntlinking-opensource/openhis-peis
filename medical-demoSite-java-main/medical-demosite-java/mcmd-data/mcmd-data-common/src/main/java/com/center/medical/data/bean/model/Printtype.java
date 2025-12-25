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
 * 销售打印分类设置(Printtype)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-18 09:11:15
 */
@Data
@TableName("md_printtype")
@ApiModel(value = "Printtype", description = "销售打印分类设置实体类")
public class Printtype extends Model<Printtype> implements Serializable {
    private static final long serialVersionUID = 630446178477646134L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "打印项目分类名称")
    private String printName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "序号")
    private Integer seq;

    @ApiModelProperty(value = "顺序 实际使用此字段排序")
    private Integer shunxu;
}
