package com.center.medical.center.qingdao.profession.entity;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class MissingFeeItemDTO implements RowMapper<MissingFeeItemDTO> {
    private String id;
    private String idExamfeeitem;
    private String examfeeitemName;

    @Override
    public MissingFeeItemDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        MissingFeeItemDTO missingFeeItemDTO = new MissingFeeItemDTO();
        missingFeeItemDTO.setId(resultSet.getString("ID"));
        missingFeeItemDTO.setIdExamfeeitem(resultSet.getString("ID_EXAMFEEITEM"));
        missingFeeItemDTO.setExamfeeitemName(resultSet.getString("EXAMFEEITEM_NAME"));
        return missingFeeItemDTO;
    }
}
