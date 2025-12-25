package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class NeedFeeItemDTO implements RowMapper<NeedFeeItemDTO> {
    private String itemId;
    private String examId;
    private String harmId;
    private String comboId;
    @Override
    public NeedFeeItemDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        NeedFeeItemDTO needFeeItemDTO = new NeedFeeItemDTO();
        needFeeItemDTO.setItemId(resultSet.getString("ITEM_ID"));
        needFeeItemDTO.setExamId(resultSet.getString("EXAM_ID"));
        needFeeItemDTO.setComboId(resultSet.getString("COMBO_ID"));
        return needFeeItemDTO;
    }
}
