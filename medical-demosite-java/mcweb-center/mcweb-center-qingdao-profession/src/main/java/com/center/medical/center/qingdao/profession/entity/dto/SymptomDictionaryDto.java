package com.center.medical.center.qingdao.profession.entity.dto;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseDictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@EqualsAndHashCode(callSuper = true)
@Data
public class SymptomDictionaryDto extends BaseDictionary implements RowMapper<SymptomDictionaryDto> {
    private String medicalId;
    private String symptom;
    private String symptomCode;
    @Override
    public SymptomDictionaryDto mapRow(ResultSet resultSet, int i) throws SQLException {
        SymptomDictionaryDto baseDictionaryDto = new SymptomDictionaryDto();
        baseDictionaryDto.setDictionaryCode(resultSet.getString("DICTIONARY_CODE"));
        baseDictionaryDto.setDictionaryType(resultSet.getString("DICTIONARY_TYPE"));
        baseDictionaryDto.setDictionaryName(resultSet.getString("DICTIONARY_NAME"));
        baseDictionaryDto.setMedicalId(resultSet.getString("MEDICAL_ID"));
        baseDictionaryDto.setSymptom(resultSet.getString("SYMPTOM"));
        baseDictionaryDto.setSymptom(resultSet.getString("SYMPTOM"));
        baseDictionaryDto.setSymptomCode(resultSet.getString("SYMPTOM_CODE"));
        baseDictionaryDto.setShandongCode(resultSet.getString("SHANDONG_CODE"));
        return baseDictionaryDto;
    }
}