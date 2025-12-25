package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class OrdinaryCheckItemDto implements RowMapper<OrdinaryCheckItemDto> {
    private String examNum;
    private String examItemPcode;
    private String hsExamItemName;
    private String hsExamItemCode;
    private String examResultType;
    private String examResult;
    private String examItemUnitCode;
    private String referenceRangeMin;
    private String referenceRangeMax;
    private String abnormal;

    @Override
    public OrdinaryCheckItemDto mapRow(ResultSet resultSet, int i) throws SQLException {
        OrdinaryCheckItemDto ordinaryCheckItemDto = new OrdinaryCheckItemDto();
        ordinaryCheckItemDto.setExamNum(resultSet.getString("EXAM_NUM"));
        ordinaryCheckItemDto.setExamItemPcode(resultSet.getString("EXAM_ITEM_PCODE"));
        ordinaryCheckItemDto.setHsExamItemName(resultSet.getString("HS_EXAM_ITEM_NAME"));
        ordinaryCheckItemDto.setHsExamItemCode(resultSet.getString("HS_EXAM_ITEM_CODE"));
        ordinaryCheckItemDto.setExamResultType(resultSet.getString("EXAM_RESULT_TYPE"));
        ordinaryCheckItemDto.setExamResult(resultSet.getString("EXAM_RESULT"));
        return ordinaryCheckItemDto;
    }

}