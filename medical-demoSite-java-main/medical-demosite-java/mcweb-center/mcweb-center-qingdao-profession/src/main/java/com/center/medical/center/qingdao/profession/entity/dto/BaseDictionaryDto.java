package com.center.medical.center.qingdao.profession.entity.dto;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseDictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseDictionaryDto extends BaseDictionary implements RowMapper<BaseDictionaryDto> {
    private String medicalId;
    @Override
    public BaseDictionaryDto mapRow(ResultSet resultSet, int i) throws SQLException {
        BaseDictionaryDto baseDictionaryDto = new BaseDictionaryDto();
        baseDictionaryDto.setDictionaryCode(resultSet.getString("DICTIONARY_CODE"));
        baseDictionaryDto.setDictionaryType(resultSet.getString("DICTIONARY_TYPE"));
        baseDictionaryDto.setDictionaryName(resultSet.getString("DICTIONARY_NAME"));
        baseDictionaryDto.setMedicalId(resultSet.getString("MEDICAL_ID"));
        baseDictionaryDto.setShandongCode(resultSet.getString("SHANDONG_CODE"));
//        baseDictionaryDto.setQingdaoCode(resultSet.getString("QINGDAO_CODE"));
        return baseDictionaryDto;
    }
}
