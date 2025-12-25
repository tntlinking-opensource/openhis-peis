package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.Hazards;
import com.center.medical.center.qingdao.profession.repository.HazardsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HazardsRepositoryImpl implements HazardsRepository {
    private final JdbcTemplate jdbcTemplate;

    public HazardsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Hazards> queryAllHazardList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        stringBuilder.append("       BD.SHANDONG_CODE,");
        stringBuilder.append("       BD.DICTIONARY_CODE,");
        stringBuilder.append("       BD.DICTIONARY_NAME,");
        stringBuilder.append("       BD.DICTIONARY_TYPE,");
        stringBuilder.append("       BD.ID as DICTIONARY_ID,");
        stringBuilder.append("       BDM.MEDICAL_ID");
        stringBuilder.append(" from BASE_DICTIONARY BD");
        stringBuilder.append("         inner join BASE_DICTIONARY_MATCH BDM on BDM.DICTIONARY_ID = BD.ID");
        stringBuilder.append(" where 1 = 1");
        stringBuilder.append("  and BD.DICTIONARY_TYPE = '312'");
        String sql = stringBuilder.toString();
        return jdbcTemplate.query(sql, new Hazards());
    }
}