package com.center.medical.appadmin.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 主页轮播图(IndexImg)表实体类
 *
 * @author ay
 * @since 2024-03-19 12:00:06
 */
@Data
@TableName("md_index_img")
@ApiModel(value = "IndexImg", description = "主页轮播图实体类")
public class IndexImg extends Model<IndexImg> implements Serializable {
    private static final long serialVersionUID = 411695110632434298L;

    @TableId(value = "img_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String imgId;


    @ApiModelProperty(value = "分中心ID")
    private String branchId;


    @ApiModelProperty(value = "图片")
    private String imgUrl;


    @ApiModelProperty(value = "说明文字,描述")
    private String des;


    @ApiModelProperty(value = "标题")
    private String title;


    @ApiModelProperty(value = "链接（当类型为小程序时，这存json对象。为外链是，这存网址。为图文时，这存id。）")
    private String link;


    @ApiModelProperty(value = "状态 0关闭1启用")
    private Integer status;


    @ApiModelProperty(value = "顺序")
    private Integer seq;


    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;


    @ApiModelProperty(value = "关联")
    private Long relation;


    @ApiModelProperty(value = "类型")
    private Integer type;


    @ApiModelProperty(value = "图片类型 0:小程序 1:外链 2:图文 3套餐")
    private Integer imgType;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String fzx;

}
