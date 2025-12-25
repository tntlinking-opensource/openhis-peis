package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class BaseZoneDto implements RowMapper<BaseZoneDto> {
    private String zoneCode;
    private String zoneName;

    @Override
    public BaseZoneDto mapRow(ResultSet resultSet, int i) throws SQLException {
        BaseZoneDto baseZoneDto = new BaseZoneDto();
        baseZoneDto.setZoneCode(resultSet.getString("ZONE_CODE"));
        baseZoneDto.setZoneName(resultSet.getString("ZONE_NAME"));
        return baseZoneDto;
    }
}