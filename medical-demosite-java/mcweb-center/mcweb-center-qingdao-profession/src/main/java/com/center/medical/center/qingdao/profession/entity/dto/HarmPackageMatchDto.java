package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class HarmPackageMatchDto implements RowMapper<HarmPackageMatchDto> {
    private String tcid;
    private String pharmId;
    private String plv;
    private String harmId;
    private String lv;

    @Override
    public HarmPackageMatchDto mapRow(ResultSet resultSet, int i) throws SQLException {
        HarmPackageMatchDto harmPackageMatchDto = new HarmPackageMatchDto();
        harmPackageMatchDto.setTcid(resultSet.getString("TCID"));
        harmPackageMatchDto.setPharmId(resultSet.getString("PHARM_ID"));
//        harmPackageMatchDto.setPlv(resultSet.getString("PLV"));
        harmPackageMatchDto.setHarmId(resultSet.getString("HARM_ID"));
//        harmPackageMatchDto.setLv(resultSet.getString("LV"));
        return harmPackageMatchDto;
    }
}