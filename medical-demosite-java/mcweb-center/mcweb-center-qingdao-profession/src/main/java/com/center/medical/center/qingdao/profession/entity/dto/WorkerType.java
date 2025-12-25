package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class WorkerType implements RowMapper<WorkerType> {
    @Override
    public WorkerType mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}