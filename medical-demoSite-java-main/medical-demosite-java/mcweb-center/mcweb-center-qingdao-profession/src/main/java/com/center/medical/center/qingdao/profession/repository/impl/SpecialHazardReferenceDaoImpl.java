package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.SpecialHazardReference;
import com.center.medical.center.qingdao.profession.repository.SpecialHazardReferenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpecialHazardReferenceDaoImpl implements SpecialHazardReferenceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SpecialHazardReference> listAll() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select distinct W.HARM_NAME as HARM_ID, ");
        stringBuilder.append("                W.UNIT, ");
        stringBuilder.append("                W.SCOPE_UPPER, ");
        stringBuilder.append("                W.SCOPER_FLOOR, ");
        stringBuilder.append("                W.GSCOPEUPPER, ");
        stringBuilder.append("                W.GSCOPERFLOOR, ");
        stringBuilder.append("                H.HARM_NAME, ");
        stringBuilder.append("                BD.DICTIONARY_CODE  as HARM_CODE, ");
        stringBuilder.append("                BD.DICTIONARY_NAME  AS HARM_NAME_2, ");
        stringBuilder.append("                BD2.DICTIONARY_CODE AS JC_CDOE, ");
        stringBuilder.append("                BD2.DICTIONARY_NAME AS JC_NAME ");
        stringBuilder.append("from MD_WHYSQZFW W ");
        stringBuilder.append("         left join MD_HARM H on H.ID = W.HARM_NAME ");
        stringBuilder.append("         left join BASE_DICTIONARY_MATCH BDM on BDM.MEDICAL_ID = W.HARM_NAME ");
        stringBuilder.append("         left join BASE_DICTIONARY BD on BD.ID = BDM.DICTIONARY_ID and DICTIONARY_TYPE = '312' ");
        stringBuilder.append("         left join BASE_DICTIONARY_MATCH BDM2 on BDM2.MEDICAL_ID = W.JC_ID ");
        stringBuilder.append("         left join BASE_DICTIONARY BD2 on BD2.ID = BDM2.DICTIONARY_ID ");
        stringBuilder.append("where (BD.DICTIONARY_CODE is not null or BD.DICTIONARY_NAME is not null) ");
        stringBuilder.append("  and (BD2.DICTIONARY_CODE is not null or BD2.DICTIONARY_NAME is not null) ");
        stringBuilder.append("  and W.ZYORJK = 0 ");
        stringBuilder.append("ORDER BY JC_CDOE, HARM_NAME_2");
        String sql = stringBuilder.toString();
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            SpecialHazardReference specialHazardReference = new SpecialHazardReference();
            specialHazardReference.setHarmId(resultSet.getString("HARM_ID"));
            specialHazardReference.setUnit(resultSet.getString("UNIT"));
            specialHazardReference.setScopeUpper(resultSet.getDouble("SCOPE_UPPER"));
            specialHazardReference.setScoperFloor(resultSet.getDouble("SCOPER_FLOOR"));
            specialHazardReference.setGscopeupper(resultSet.getDouble("GSCOPEUPPER"));
            specialHazardReference.setGscoperfloor(resultSet.getDouble("GSCOPERFLOOR"));
            specialHazardReference.setHarmName(resultSet.getString("HARM_NAME"));
            specialHazardReference.setHarmCode(resultSet.getString("HARM_CODE"));
            specialHazardReference.setHarmName2(resultSet.getString("HARM_NAME_2"));
            specialHazardReference.setJcCdoe(resultSet.getString("JC_CDOE"));
            specialHazardReference.setJcName(resultSet.getString("JC_NAME"));
            return specialHazardReference;
        });
    }
}
