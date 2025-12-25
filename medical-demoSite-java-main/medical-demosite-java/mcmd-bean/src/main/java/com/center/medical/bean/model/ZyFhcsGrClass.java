package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC个人防护用品种类(ZyFhcsGrClass)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:19
 */
@Data
@TableName("md_zy_fhcs_gr_class")
@ApiModel(value = "ZyFhcsGrClass", description = "JC个人防护用品种类实体类")
public class ZyFhcsGrClass extends Model<ZyFhcsGrClass> implements Serializable {
    private static final long serialVersionUID = -73054027107988838L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "防护用品种类代码")
    private String defendIndividualClassCode;

    @ApiModelProperty(value = "防护用品种类")
    private String defendIndividualClass;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
