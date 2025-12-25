package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.PeiUploadData;
import com.center.medical.center.qingdao.profession.repository.PeisStateQueryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class PeisStateQueryRepositoryImpl implements PeisStateQueryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PeisStateQueryRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<PeiUploadData> queryByPatientCodes(List<String> patientCodes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select PS.PATIENTCODE, ");
        stringBuilder.append(" P.PATIENTNAME, ");
        stringBuilder.append("       S.LICENSE_NAME, ");
        stringBuilder.append("       S.KHDWMC, ");
        stringBuilder.append("       IFNULL(CC.TJTCMC, CM.TJTCMC) TJTCMC, ");
        stringBuilder.append("       S.XSJL, ");
        stringBuilder.append("       PS.JINAN_MSG, ");
        stringBuilder.append("       case ");
        stringBuilder.append("           when PS.JINAN_STATUS = '2' then '失败' ");
        stringBuilder.append("           when PS.JINAN_STATUS = '1' then '成功' ");
        stringBuilder.append("           when PS.JINAN_STATUS = '0' then '未上传' ");
        stringBuilder.append("           when PS.JINAN_STATUS = '3' then '不能上传' ");
        stringBuilder.append("           else '其他' end as      JINAN_STATUS ");
        stringBuilder.append("from MD_PEIS_STATE PS ");
        stringBuilder.append("         left join MD_PEISPATIENT P on P.PATIENTCODE = PS.PATIENTCODE ");
        stringBuilder.append("         left join CRM_SELLCUSTOMER S on S.ID = P.ID_ORG ");
        stringBuilder.append("         left join MD_CREATECOMBO CC on CC.ID = P.ID_TJTC ");
        stringBuilder.append("         left join MD_CREATEMEAL CM on CM.ID = P.ID_TJTC ");
        stringBuilder.append("where PS.PATIENTCODE in (:patientCode)");
        stringBuilder.append(" order by XSJL,LICENSE_NAME,KHDWMC,TJTCMC,PATIENTCODE ");
        String sql = stringBuilder.toString();
        return namedParameterJdbcTemplate.query(sql, Collections.singletonMap("patientCode",patientCodes), new PeiUploadData());
    }
}