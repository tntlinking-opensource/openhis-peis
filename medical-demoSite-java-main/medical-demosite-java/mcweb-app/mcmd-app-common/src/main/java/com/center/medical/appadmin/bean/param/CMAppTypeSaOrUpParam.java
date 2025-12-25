package com.center.medical.appadmin.bean.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加或更新参数
 */
@Data
public class CMAppTypeSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 3403033633447284312L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;


    @ApiModelProperty(value = "名称")
    private String name;


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @ApiModelProperty(value = "备注")
    private String note;


    @ApiModelProperty(value = "序号")
    private Integer seq;

}
