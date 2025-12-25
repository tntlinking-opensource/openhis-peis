package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.ConclusionDto;
import com.center.medical.center.qingdao.profession.repository.ConclusionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ConclusionRepositoryImpl implements ConclusionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public ConclusionRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ConclusionDto> queryByPatientCode(String patientCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", patientCode);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select CP.PATIENTCODE,  ");
        stringBuilder.append("       ST.VERDICT,  ");
        stringBuilder.append("       ST.SUMMARY_ID,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DIAGNOSIS_CODE,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DIAGNOSIS,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DISEAST,  ");
        stringBuilder.append("       ZVS.DIAGNOSIS,  ");
        stringBuilder.append("       ZVS.OCCUPATION_SUMMARY,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DISEAST,  ");
        stringBuilder.append("       H.HARM_NAME,ST.POSISTIVE  ");
        stringBuilder.append("from MD_COMMENTS_PROGESSIONAL CP  ");
        stringBuilder.append("         left join MD_ZY_VS_SUMMARY ZVS on ZVS.ID = CP.PROGESSIONAL_ID  ");
        stringBuilder.append("         left join MD_ZY_SUMMARY ZS on ZS.OCCUPATION_SUMMARY_CODE = ZVS.OCCUPATION_SUMMARY_CODE  ");
        stringBuilder.append("         left join MD_HARM H on H.ID = ZVS.OCCUPATION_DIAGNOSIS  ");
        stringBuilder.append("         inner join MD_SECTION_TOTAL ST on ST.PATIENTCODE = CP.PATIENTCODE and st.disease_health=1 ");
        stringBuilder.append("where cp.PATIENTCODE = :list");
        String sql= stringBuilder.toString();
        return namedParameterJdbcTemplate.query(sql, map, new ConclusionDto());
    }


    /**
     * 正常流程，进行多次复查并下总检结论
     * @param patientCode
     * @param harmId
     * @return
     */
    @Override
    public List<ConclusionDto> queryByFirstPatientCode(String patientCode,String harmId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("patientcode", patientCode);
        map.put("harmId", harmId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select CP.PATIENTCODE,  ");
        stringBuilder.append("       ST.VERDICT,  ");
        stringBuilder.append("       ST.SUMMARY_ID,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DIAGNOSIS_CODE,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DIAGNOSIS,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DISEAST,  ");
        stringBuilder.append("       ZVS.DIAGNOSIS,  ");
        stringBuilder.append("       ZVS.OCCUPATION_SUMMARY,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DISEAST,  ");
        stringBuilder.append("       H.HARM_NAME,ST.POSISTIVE  ");
        stringBuilder.append("from MD_COMMENTS_PROGESSIONAL CP  ");
        stringBuilder.append("         inner join MD_peispatient p on p.patientcode=cp.patientcode  ");
        stringBuilder.append("         inner join MD_ZY_VS_SUMMARY ZVS on ZVS.ID = CP.PROGESSIONAL_ID  ");
        stringBuilder.append("         inner join MD_ZY_SUMMARY ZS on ZS.OCCUPATION_SUMMARY_CODE = ZVS.OCCUPATION_SUMMARY_CODE  ");
        stringBuilder.append("         inner join MD_HARM H on H.ID = ZVS.OCCUPATION_DIAGNOSIS  ");
        stringBuilder.append("         inner join MD_SECTION_TOTAL ST on ST.PATIENTCODE = CP.PATIENTCODE and st.disease_health=1 ");
        stringBuilder.append("where p.inpatientno = :patientcode AND ZVS.OCCUPATION_DIAGNOSIS=:harmId  and zs.id in ('1','3','4','5')   order by p.dateregister desc    ");
        String sql= stringBuilder.toString();
        return namedParameterJdbcTemplate.query(sql, map, new ConclusionDto());
    }

    /**
     * 特殊情况，没有来复查，查询最终结论
     * @param patientCode
     * @param harmId
     * @return
     */
    @Override
    public List<ConclusionDto> queryFinalByPatientCode(String patientCode,String harmId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("patientcode", patientCode);
        map.put("harmId", harmId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select '' PATIENTCODE,  ");
        stringBuilder.append("       ST.VERDICT VERDICT,  ");
        stringBuilder.append("       '' SUMMARY_ID,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DIAGNOSIS_CODE,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DIAGNOSIS,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DISEAST,  ");
        stringBuilder.append("       ZVS.DIAGNOSIS,  ");
        stringBuilder.append("       ZVS.OCCUPATION_SUMMARY,  ");
        stringBuilder.append("       ZVS.OCCUPATION_DISEAST,  ");
        stringBuilder.append("       H.HARM_NAME,ST.POSISTIVE  ");
        stringBuilder.append("from MD_ZY_FINAL_SUMMARY CP  ");
        stringBuilder.append("         INNER join MD_ZY_VS_SUMMARY ZVS on ZVS.ID = CP.PROGESSIONAL  ");
        stringBuilder.append("         INNER join MD_HARM H on H.ID = ZVS.OCCUPATION_DIAGNOSIS  ");
        stringBuilder.append("         inner join MD_SECTION_TOTAL ST on ST.PATIENTCODE = CP.PATIENTCODE and st.disease_health=1 ");
        stringBuilder.append("where cp.PATIENTCODE = :patientcode AND ZVS.OCCUPATION_DIAGNOSIS=:harmId      ");
        String sql= stringBuilder.toString();
        return namedParameterJdbcTemplate.query(sql, map, new ConclusionDto());
    }



    @Override
    public String querySectionTotal(String patientCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select VERDICT  ");
        stringBuilder.append("from MD_SECTION_TOTAL  ");
        stringBuilder.append("where PATIENTCODE = ?");
        String sql= stringBuilder.toString();
        return jdbcTemplate.queryForObject(sql, String.class, patientCode);
    }

}