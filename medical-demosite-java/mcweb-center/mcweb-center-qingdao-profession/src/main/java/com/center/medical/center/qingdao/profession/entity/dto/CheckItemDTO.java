package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CheckItemDTO implements RowMapper<CheckItemDTO> {
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
    public CheckItemDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        CheckItemDTO checkItemDTO = new CheckItemDTO();
        checkItemDTO.setExamItemPcode(resultSet.getString("EXAM_NUM"));
        checkItemDTO.setExamItemPcode(resultSet.getString("EXAM_ITEM_PCODE"));
        checkItemDTO.setHsExamItemCode(resultSet.getString("HS_EXAM_ITEM_CODE"));
        checkItemDTO.setHsExamItemName(resultSet.getString("HS_EXAM_ITEM_NAME"));
        checkItemDTO.setExamResultType(resultSet.getString("EXAM_RESULT_TYPE"));
        checkItemDTO.setExamResult(resultSet.getString("EXAM_RESULT"));
        checkItemDTO.setExamItemUnitCode(resultSet.getString("EXAM_ITEM_UNIT_CODE"));
        checkItemDTO.setReferenceRangeMin(resultSet.getString("REFERENCE_RANGE_MIN"));
        checkItemDTO.setReferenceRangeMax(resultSet.getString("REFERENCE_RANGE_MAX"));
        checkItemDTO.setAbnormal(resultSet.getString("ABNORMAL"));
        return checkItemDTO;
    }
}