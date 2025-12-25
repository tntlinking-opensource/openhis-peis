package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 需单独处理的数据表
 */
@Data
public class IndividualDto implements Serializable {
    private static final long serialVersionUID = 929564788348371726L;

    @ApiModelProperty(value = "oracle数据表名")
    private String oracleTable;

    @ApiModelProperty(value = "mysql数据表名")
    private String mysqlTable;

    @ApiModelProperty(value = "oracle字段名")
    private List<String> oracleField;

    @ApiModelProperty(value = "mysql字段名")
    private List<String> mysqlField;


    public IndividualDto(String oracleTable, String mysqlTable, List<String> oracleField, List<String> mysqlField) {
        this.oracleTable = oracleTable;
        this.mysqlTable = mysqlTable;
        this.oracleField = oracleField;
        this.mysqlField = mysqlField;
    }

    public IndividualDto() {
    }
}
