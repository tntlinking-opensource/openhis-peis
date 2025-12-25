package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hazards implements RowMapper<Hazards> {
    private String shandongCode;
    private String dictionaryCode;
    private String dictionaryName;
    private String dictionaryId;
    private String medicalId;

    @Override
    public Hazards mapRow(ResultSet resultSet, int i) throws SQLException {
        shandongCode = resultSet.getString("SHANDONG_CODE");
        dictionaryCode = resultSet.getString("DICTIONARY_CODE");
        dictionaryName = resultSet.getString("DICTIONARY_NAME");
        dictionaryId = resultSet.getString("DICTIONARY_ID");
        medicalId = resultSet.getString("MEDICAL_ID");
        return this;
    }
}