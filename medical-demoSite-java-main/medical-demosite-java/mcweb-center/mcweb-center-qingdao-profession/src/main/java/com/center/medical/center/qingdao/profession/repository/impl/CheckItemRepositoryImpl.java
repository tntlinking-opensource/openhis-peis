package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.CheckItemReference;
import com.center.medical.center.qingdao.profession.repository.CheckItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CheckItemRepositoryImpl implements CheckItemRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CheckItemReference> listAll() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select distinct BD.DICTIONARY_CODE, ");
        stringBuilder.append(" BD.DICTIONARY_NAME, ");
        stringBuilder.append(" BE.VALUETYPE, ");
        stringBuilder.append(" ifnull(BE.VALUEUNIT, '%')                            as VALUEUNIT, ");
//        stringBuilder.append(" ifnull(BE.VALUEMALEMIN, '无')   as VALUEMALEMIN, ");
//        stringBuilder.append(" ifnull(BE.VALUEMALEMAX, '无')   as VALUEMALEMAX, ");
//        stringBuilder.append(" ifnull(BE.VALUEMALEDEF, '无')   as VALUEMALEDEF, ");
//        stringBuilder.append(" ifnull(BE.VALUEFEMALEDEF, '无') as VALUEFEMALEDEF, ");
//        stringBuilder.append(" ifnull(BE.VALUEFEMALEMIN, '无') as VALUEFEMALEMIN, ");
//        stringBuilder.append(" ifnull(BE.VALUEFEMALEMAX, '无') as VALUEFEMALEMAX, ");
//        stringBuilder.append(" ifnull(BE.VALUEFEMALEDEF, '无') as VALUEFEMALEDEF ");

        stringBuilder.append(" ifnull(BE.VALUEMALEMIN, '')   as VALUEMALEMIN, ");
        stringBuilder.append(" ifnull(BE.VALUEMALEMAX, '')   as VALUEMALEMAX, ");
        stringBuilder.append(" ifnull(BE.VALUEMALEDEF, '')   as VALUEMALEDEF, ");
        stringBuilder.append(" ifnull(BE.VALUEFEMALEDEF, '') as VALUEFEMALEDEF, ");
        stringBuilder.append(" ifnull(BE.VALUEFEMALEMIN, '') as VALUEFEMALEMIN, ");
        stringBuilder.append(" ifnull(BE.VALUEFEMALEMAX, '') as VALUEFEMALEMAX, ");
        stringBuilder.append(" ifnull(BE.VALUEFEMALEDEF, '') as VALUEFEMALEDEF ");

        stringBuilder.append(" from md_BASEXAMLTEM BE ");
        stringBuilder.append(" right outer join BASE_DICTIONARY_MATCH BDM on BDM.MEDICAL_ID = BE.ID ");
        stringBuilder.append(" right outer join BASE_DICTIONARY BD on BD.ID = BDM.DICTIONARY_ID ");
        stringBuilder.append(" inner join BASE_DICTIONARY_CLASS BDC on BDC.CLASS_NAME = '体检项目' and BDC.CLASS_CODE = BD.DICTIONARY_TYPE");
        String sql = stringBuilder.toString();
        try{
            return jdbcTemplate.query(sql, (resultSet, i) -> {
                CheckItemReference checkItemReference = new CheckItemReference();
                checkItemReference.setDictionaryCode(resultSet.getString("DICTIONARY_CODE"));
                checkItemReference.setDictionaryName(resultSet.getString("DICTIONARY_NAME"));
                checkItemReference.setValuemalemin(resultSet.getString("VALUEMALEMIN"));
                checkItemReference.setValuemalemax(resultSet.getString("VALUEMALEMAX"));
                checkItemReference.setValuemaledef(resultSet.getString("VALUEMALEDEF"));
                checkItemReference.setValuefemalemin(resultSet.getString("VALUEFEMALEMIN"));
                checkItemReference.setValuefemalemax(resultSet.getString("VALUEFEMALEMAX"));
                checkItemReference.setValuefemaledef(resultSet.getString("VALUEFEMALEDEF"));
                return checkItemReference;
            });
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否勾选了pacs体征词
     * @param patientcode
     * @param signId
     * @return
     */
    @Override
    public boolean hasPacsSign(String patientcode,String signId){
        String sql="select count(*) from MD_PACS_SECTION_RESULT_TWO where patientcode=? and nodule=? ";
        int count=jdbcTemplate.queryForObject(sql,Integer.class,patientcode,signId);
        return count>0;
    }
}
