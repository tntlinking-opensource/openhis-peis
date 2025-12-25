package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.repository.PeispatientQueryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PeispatientQueryRepositoryImpl implements PeispatientQueryRepository {
    private final JdbcTemplate jdbcTemplate;

    public PeispatientQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public String queryWorkType(String patientCode) {
        String sql = "select WORKTYPE_ID    from MD_PEISPATIENT    where PATIENTCODE = ?";
        return jdbcTemplate.queryForObject(sql, String.class, patientCode);
    }

}