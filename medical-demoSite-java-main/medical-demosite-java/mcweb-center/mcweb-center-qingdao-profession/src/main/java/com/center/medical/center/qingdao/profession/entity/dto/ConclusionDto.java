package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


@Data
public class ConclusionDto implements RowMapper<ConclusionDto> {
    private String patientcode;
    private String summaryId;
    private String occupationDiagnosis;
    private String occupationDiagnosisCode;
    private String diagnosis;
    private String verdict;
    private String harmName;
    private String occupationSummary;
    private String occupationDiseast;
    private String posistive;
    @Override
    public ConclusionDto mapRow(ResultSet resultSet, int i) throws SQLException {
        ConclusionDto conclusionDto = new ConclusionDto();
        conclusionDto.setPosistive(resultSet.getString("POSISTIVE"));
        conclusionDto.setPatientcode(resultSet.getString("PATIENTCODE"));
        conclusionDto.setOccupationDiagnosis(resultSet.getString("OCCUPATION_DIAGNOSIS"));
        conclusionDto.setOccupationDiagnosisCode(resultSet.getString("OCCUPATION_DIAGNOSIS_CODE"));
        conclusionDto.setDiagnosis(resultSet.getString("DIAGNOSIS"));
        conclusionDto.setSummaryId(resultSet.getString("SUMMARY_ID"));
        conclusionDto.setVerdict(resultSet.getString("VERDICT"));
        conclusionDto.setOccupationSummary(resultSet.getString("OCCUPATION_SUMMARY"));
        conclusionDto.setOccupationDiseast(resultSet.getString("OCCUPATION_DISEAST"));
        return conclusionDto;
    }
}