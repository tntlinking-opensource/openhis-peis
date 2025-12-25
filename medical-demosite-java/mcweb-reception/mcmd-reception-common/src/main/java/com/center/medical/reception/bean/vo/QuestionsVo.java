package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 问题的实体类
 */
@Data
public class QuestionsVo implements Serializable {
    private static final long serialVersionUID = -2133629023367909279L;

    @ApiModelProperty(value = "问题id")
    private Integer questionId;

    @ApiModelProperty(value = "是否多选，1是0否")
    private Integer multiSelect;

    @ApiModelProperty(value = "问题类型")
    private Integer questionType;

    @ApiModelProperty(value = "flag")
    private Integer flag;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "选项")
    private String[] items;


    @ApiModelProperty(value = "性别")
    private String Sex;

    public QuestionsVo(Integer questionId, Integer multiSelect, Integer questionType, Integer flag, String description, String[] items) {
        this.questionId = questionId;
        this.multiSelect = multiSelect;
        this.questionType = questionType;
        this.flag = flag;
        this.description = description;
        this.items = items;
    }


    public QuestionsVo(Integer questionId, Integer multiSelect, Integer questionType, String sex, Integer flag, String description, String[] items) {
        this.questionId = questionId;
        this.multiSelect = multiSelect;
        this.questionType = questionType;
        this.flag = flag;
        this.description = description;
        this.items = items;
        Sex = sex;
    }

    public QuestionsVo() {
    }
}
