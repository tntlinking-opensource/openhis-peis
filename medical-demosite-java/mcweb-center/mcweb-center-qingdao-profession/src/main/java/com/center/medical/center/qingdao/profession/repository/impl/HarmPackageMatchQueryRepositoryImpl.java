package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.HarmPackageMatchDto;
import com.center.medical.center.qingdao.profession.repository.HarmPackageMatchQueryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Repository
public class HarmPackageMatchQueryRepositoryImpl implements HarmPackageMatchQueryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HarmPackageMatchQueryRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<HarmPackageMatchDto> query(String tcid, String jhys) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select HPM.TCID, ");
        stringBuilder.append("       HPM.PHARM_ID, ");
//        stringBuilder.append("       H2.LV  as   PLV, ");
        stringBuilder.append("      GROUP_CONCAT(HPM.HARM_ID order by HARM_ID)  as HARM_ID ");
//        stringBuilder.append("       listagg(H.LV, ',') within group ( order by HPM.HARM_ID)   as  LV ");
        stringBuilder.append("from MD_HARM_PACKAGE_MATCH HPM ");
        stringBuilder.append("         left join MD_HARM H on HPM.HARM_ID = H.ID ");
        stringBuilder.append("         left join MD_HARM H2 on HPM.PHARM_ID = H.ID ");
        stringBuilder.append("where HPM.TCID = :TCID ");
        stringBuilder.append("  and HPM.PHARM_ID in (:PHARM_ID) ");
        stringBuilder.append("group by HPM.TCID, HPM.PHARM_ID, H2.LV");
        String sql = stringBuilder.toString();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("TCID", tcid);
        paramMap.put("PHARM_ID", Arrays.asList(jhys.split(",")));
        List<HarmPackageMatchDto> query = namedParameterJdbcTemplate.query(sql, paramMap, new HarmPackageMatchDto());
        return query;
    }
}