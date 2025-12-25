package com.center.medical.center.qingdao.profession.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.center.medical.center.qingdao.profession.entity.dto.ReviewPatientDTO;
import com.center.medical.center.qingdao.profession.entity.persistent.PeisState;
import com.center.medical.center.qingdao.profession.entity.properties.ConfigProperties;
import com.center.medical.center.qingdao.profession.repository.PeisStateExtRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PeisStateExtRepositoryImpl implements PeisStateExtRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ConfigProperties properties;

    public PeisStateExtRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, ConfigProperties properties) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.properties = properties;
    }

    @Override
    public List<String> findPatientCodes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" select PATIENTCODE ");
        stringBuilder.append(" from MD_PEISPATIENT ");
        stringBuilder.append(" where ID_EXAMTYPE in ('1') ");
        stringBuilder.append(" and PATIENTCODE not in (select PATIENTCODE ");
        stringBuilder.append(" from MD_PEIS_STATE ");
        stringBuilder.append(" where (JINAN_STATUS <> 1 or JINAN_STATUS is null)) ");
//        stringBuilder.append(" and ZYTJZT >= 2");
        stringBuilder.append(" and ZYTJZT >= 7 ");
        stringBuilder.append(" and F_REGISTERED = 1 ");
//        stringBuilder.append(" and DATEREGISTER is not null ");
        stringBuilder.append(" and ID_TJTC not in (select ID ");
        stringBuilder.append(" from MD_CREATECOMBO ");
        stringBuilder.append(" where TJTCMC like '%特种作业%' ");
        stringBuilder.append(" union all ");
        stringBuilder.append(" select ID ");
        stringBuilder.append(" from MD_CREATECOMBO ");
        stringBuilder.append(" where TJTCMC like '%特种作业%') ");
        String sql = stringBuilder.toString();
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public void insert(PeisState peisState) {
        peisState.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        peisState.setCreatedate(new Date());
        peisState.setModifydate(new Date());
        Map<String, Object> map = BeanUtil.beanToMap(peisState);
        namedParameterJdbcTemplate.update("insert into MD_PEIS_STATE (ID, CREATEDATE,MODIFYDATE, PATIENTCODE, JINAN_STATUS, JINAN_MSG) values (:id, :createdate,:modifydate, :patientcode, :jinanStatus, :jinanMsg)", map);

    }

    @Override
    public void update(PeisState peisState) {
        String sql = "update MD_PEIS_STATE  set MODIFYDATE  = current_timestamp,      JINAN_STATUS=:jinanStatus,      JINAN_MSG=:jinanMsg  where PATIENTCODE = :patientcode";
        peisState.setCreatedate(new Date());
        Map<String, Object> map = BeanUtil.beanToMap(peisState);
        peisState.setModifydate(new Date());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<String> findByCreateDateRangle(Date startDate, Date endDate) {
        String start = DateUtil.format(startDate, "yyyy-MM-dd");
//        String end = DateUtil.format(endDate, "yyyy-MM-dd");
        if (properties.getHasArchive()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select distinct PATIENTCODE ");
            stringBuilder.append("from ( ");
            stringBuilder.append(" select PATIENTCODE ");
            stringBuilder.append(" from MD_PEISPATIENT ");
            stringBuilder.append(" where ID_EXAMTYPE in ('1','2') ");
            stringBuilder.append(" and PATIENTCODE in (select PATIENTCODE ");
            stringBuilder.append(" from MD_PEIS_STATE ");
            stringBuilder.append(" where (JINAN_STATUS = 2 or JINAN_STATUS is null or jinan_status=0)) ");
            stringBuilder.append(" and DATEREGISTER >= STR_TO_DATE(:start,'%Y-%m-%d') ");
//            stringBuilder.append(" and DATEREGISTER < STR_TO_DATE(:end, '%Y-%m-%d') + 1 ");//注意：mysql不能直接+1
//            stringBuilder.append(" and ZYTJZT >= 2 ");
            stringBuilder.append(" and ZYTJZT >= 7 ");
            stringBuilder.append(" and F_REGISTERED = 1 ");
//            stringBuilder.append(" and DATEREGISTER is not null ");
            stringBuilder.append(" and ID_TJTC not in (select ID ");
            stringBuilder.append(" from MD_CREATECOMBO ");
            stringBuilder.append(" where TJTCMC like '%特种作业%' ");
            stringBuilder.append(" union all ");
            stringBuilder.append(" select ID ");
            stringBuilder.append(" from MD_CREATEMEAL ");
            stringBuilder.append(" where TJTCMC like '%特种作业%') ");
            stringBuilder.append(") t ");
            String sql = stringBuilder.toString();
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("start", start);
//            paramMap.put("end", end);
            return namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select distinct PATIENTCODE ");
            stringBuilder.append("from ( ");
            stringBuilder.append(" select PATIENTCODE ");
            stringBuilder.append(" from MD_PEISPATIENT ");
            stringBuilder.append(" where ID_EXAMTYPE in ('1','2') ");
            stringBuilder.append(" and PATIENTCODE in (select PATIENTCODE ");
            stringBuilder.append(" from MD_PEIS_STATE ");
            stringBuilder.append(" where ( JINAN_STATUS = 2 or JINAN_STATUS is null or jinan_status=0)) ");
            stringBuilder.append(" and DATEREGISTER >= STR_TO_DATE(:start, '%Y-%m-%d') ");
//            stringBuilder.append(" and DATEREGISTER <= STR_TO_DATE(:end, '%Y-%m-%d') ");
//            stringBuilder.append(" and ZYTJZT >= 2 ");
            stringBuilder.append(" and ZYTJZT >= 7 ");
            stringBuilder.append(" and F_REGISTERED = 1 ");
//            stringBuilder.append(" and DATEREGISTER is not null ");
            stringBuilder.append(" and ID_TJTC not in (select ID ");
            stringBuilder.append(" from MD_CREATECOMBO ");
            stringBuilder.append(" where TJTCMC like '%特种作业%' ");
            stringBuilder.append(" union all ");
            stringBuilder.append(" select ID ");
            stringBuilder.append(" from MD_CREATEMEAL ");
            stringBuilder.append(" where TJTCMC like '%特种作业%') ");
            stringBuilder.append(" ) T ");
            String sql = stringBuilder.toString();
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("start", start);
//            paramMap.put("end", end);
            return namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
        }
    }

    @Override
    public List<String> queryByOrderNo(String orderNo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" select PATIENTCODE   ");
        stringBuilder.append(" from MD_PEISPATIENT   ");
        stringBuilder.append(" where numorgresv = ?   ");
//        stringBuilder.append(" and ZYTJZT >= 2 ");
        stringBuilder.append(" and ZYTJZT >= 7 ");
        stringBuilder.append(" and F_REGISTERED = 1 ");
        stringBuilder.append(" and DATEREGISTER is not null ");
        stringBuilder.append(" and ID_EXAMTYPE in ('1','2') ");
        stringBuilder.append(" and PATIENTCODE in (select PATIENTCODE ");
        stringBuilder.append(" from MD_PEIS_STATE ");
        stringBuilder.append(" where ( JINAN_STATUS <> 1 or JINAN_STATUS <> 2 or JINAN_STATUS is null)) ");
//        stringBuilder.append(" and ZYTJZT >= 2 ");
        stringBuilder.append(" and ZYTJZT >= 7 ");
        stringBuilder.append(" and F_REGISTERED = 1 ");
//        stringBuilder.append(" and DATEREGISTER is not null ");
        stringBuilder.append(" and ID_TJTC not in (select ID ");
        stringBuilder.append(" from MD_CREATECOMBO ");
        stringBuilder.append(" where TJTCMC like '%特种作业%' ");
        stringBuilder.append(" union all ");
        stringBuilder.append(" select ID ");
        stringBuilder.append(" from MD_CREATEMEAL ");
        stringBuilder.append(" where TJTCMC like '%特种作业%')");
        String sql = stringBuilder.toString();
        return jdbcTemplate.queryForList(sql, String.class, orderNo);
    }

    @Override
    public List<ReviewPatientDTO> findReviewByDateRangle(Date startDate, Date endDate) {
        String start = DateUtil.format(startDate, "yyyy-MM-dd");
        String end = DateUtil.format(endDate, "yyyy-MM-dd");
        if (properties.getHasArchive()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select distinct PATIENTCODE,INPATIENTNO ");
            stringBuilder.append("from (select PATIENTCODE,INPATIENTNO ");
            stringBuilder.append("      from MD_PEISPATIENT ");
            stringBuilder.append("      where ID_EXAMTYPE in ('3') ");
            stringBuilder.append("        and PATIENTCODE in (select PATIENTCODE ");
            stringBuilder.append("                            from MD_PEIS_STATE ");
            stringBuilder.append("                            where (JINAN_STATUS = 2 or JINAN_STATUS is null  or jinan_status=0)) ");
            stringBuilder.append("        and DATEREGISTER >= STR_TO_DATE(:start, '%Y-%m-%d') ");
//            stringBuilder.append("        and DATEREGISTER < STR_TO_DATE(:end, '%Y-%m-%d') + 1 ");//注意mysql不能直接加1
//            stringBuilder.append("        and ZYTJZT >= 2 ");
            stringBuilder.append(" and ZYTJZT >= 7 ");
            stringBuilder.append("        and F_REGISTERED = 1 ");
//            stringBuilder.append("        and DATEREGISTER is not null ");
            stringBuilder.append("        ) T");
            String sql = stringBuilder.toString();
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("start", start);
//            paramMap.put("end", end);
            return namedParameterJdbcTemplate.query(sql, paramMap, (resultSet, i) -> {
                ReviewPatientDTO reviewPatientDTO = new ReviewPatientDTO();
                reviewPatientDTO.setPatientCode(resultSet.getString("PATIENTCODE"));
                reviewPatientDTO.setPrePatientCode(resultSet.getString("INPATIENTNO"));
                return reviewPatientDTO;
            });
        }else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select distinct PATIENTCODE,INPATIENTNO ");
            stringBuilder.append("from (select PATIENTCODE,INPATIENTNO ");
            stringBuilder.append("      from MD_PEISPATIENT ");
            stringBuilder.append("      where ID_EXAMTYPE in ('3') ");
            stringBuilder.append("        and PATIENTCODE in (select PATIENTCODE ");
            stringBuilder.append("                            from MD_PEIS_STATE ");
            stringBuilder.append("                            where (JINAN_STATUS = 2 or JINAN_STATUS is null  or jinan_status=0)) ");
            stringBuilder.append("        and DATEREGISTER >= STR_TO_DATE(:start, '%Y-%m-%d') ");
//            stringBuilder.append("        and DATEREGISTER < STR_TO_DATE(:end, '%Y-%m-%d') + 1 ");
//            stringBuilder.append("        and ZYTJZT >= 2 ");
            stringBuilder.append(" and ZYTJZT >= 7 ");
            stringBuilder.append("        and F_REGISTERED = 1 ");
//            stringBuilder.append("        and DATEREGISTER is not null ");
            stringBuilder.append("      ) T");
            String sql = stringBuilder.toString();
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("start", start);
//            paramMap.put("end", end);
            return namedParameterJdbcTemplate.query(sql, paramMap, (resultSet, i) -> {
                ReviewPatientDTO reviewPatientDTO = new ReviewPatientDTO();
                reviewPatientDTO.setPatientCode(resultSet.getString("PATIENTCODE"));
                reviewPatientDTO.setPrePatientCode(resultSet.getString("INPATIENTNO"));
                return reviewPatientDTO;
            });
        }
    }

}
