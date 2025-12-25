package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.ComboexamitemDto;
import com.center.medical.center.qingdao.profession.repository.ComboexamitemQueryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComboexamitemQueryRepositoryImpl implements ComboexamitemQueryRepository {
    private final JdbcTemplate jdbcTemplate;

    public ComboexamitemQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ComboexamitemDto> list(String JHYS, String ZYTJLB) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select HARM_ID, ");
        stringBuilder.append("       EXAM_ID, ");
        stringBuilder.append("       ITEM_ID, ");
        stringBuilder.append("       C.ID, ");
        stringBuilder.append("       COMBO_ID, ");
        stringBuilder.append("       MEDICAL_TYPE, ");
        stringBuilder.append("       KS_ID, ");
        stringBuilder.append(" B.EXAMITEM_CODE, ");
        stringBuilder.append("       B.EXAMITEM_NAME, ");
        stringBuilder.append("  T.type, ");
        stringBuilder.append("       T.EXAMITEMVALUESREPORT, ");
        stringBuilder.append("  SRT.POSISTIVE, " +
                "       SRT.NODULE ");
        stringBuilder.append("from MD_COMBOEXAMITEM C");
        stringBuilder.append("  inner join MD_BASEXAMLTEM B on B.ID = C.EXAM_ID ");
        stringBuilder.append("left join (select ID_EXAMFEEITEM, ID_EXAMITEM, '1' as type, EXAMITEMVALUESREPORT, F_LABITEMNORMAL  ");
        stringBuilder.append("                    from MD_PEISPATIENTEXAMITEM  ");
        stringBuilder.append("                    where DEP_ID = '19'  ");
        stringBuilder.append("                      and EXAMITEMVALUESNUMBER is null  ");
        stringBuilder.append("                    union  ");
        stringBuilder.append("                    select ID_EXAMFEEITEM, ID_EXAMITEM, '2' as type, EXAMITEMVALUESREPORT, F_LABITEMNORMAL  ");
        stringBuilder.append("                    from MD_PEISPATIENTEXAMITEM  ");
        stringBuilder.append("                    where DEP_ID = '19'  ");
        stringBuilder.append("                      and EXAMITEMVALUESNUMBER is not null  ");
        stringBuilder.append("                    union  ");
        stringBuilder.append("                    select ID_EXAMFEEITEM, ID_EXAMITEM, '1' as type, EXAMITEMVALUESREPORT, F_LABITEMNORMAL  ");
        stringBuilder.append("                    from MD_PEISPATIENTEXAMITEM  ");
        stringBuilder.append("                    where DEP_ID <> '19') T on T.ID_EXAMITEM = C.EXAM_ID and T.ID_EXAMFEEITEM = C.ITEM_ID  ");
        stringBuilder.append("         left join MD_SECTION_RESULT_TWO SRT on SRT.VERDICT_ID = t.ID_EXAMITEM and SRT.CHARGES_ID = T.ID_EXAMFEEITEM ");
        stringBuilder.append("where COMBO_ID in (select ID ");
        stringBuilder.append("                   from MD_CREATECOMBO ");
        stringBuilder.append("                   where JHYS = ? ");
        stringBuilder.append("                     and IS_DELETE = '0' ");
        stringBuilder.append("                     and ZYTJLB = ?)");
        String sql = stringBuilder.toString();
        return jdbcTemplate.query(sql, new ComboexamitemDto(), JHYS, ZYTJLB);
    }

    @Override
    public List<ComboexamitemDto> list(String patientcode, String JHYS, String ZYTJLB) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select distinct HARM_ID, ");
        stringBuilder.append("                EXAM_ID, ");
        stringBuilder.append("                ITEM_ID, ");
        stringBuilder.append("                C.ID, ");
        stringBuilder.append("                COMBO_ID, ");
        stringBuilder.append("                MEDICAL_TYPE, ");
        stringBuilder.append("                KS_ID, ");
        stringBuilder.append("                B.EXAMITEM_CODE, ");
        stringBuilder.append("                B.EXAMITEM_NAME, ");
        stringBuilder.append("                T.type, ");
        stringBuilder.append("                T.EXAMITEMVALUESREPORT, ");
        stringBuilder.append("                SRT.POSISTIVE, ");
        stringBuilder.append("                SRT.NODULE ");
        stringBuilder.append("from MD_COMBOEXAMITEM C ");
        stringBuilder.append("         inner join MD_BASEXAMLTEM B on B.ID = C.EXAM_ID ");
        stringBuilder.append("         inner join (select ID_EXAMFEEITEM, ");
        stringBuilder.append("                            ID_EXAMITEM, ");
        stringBuilder.append("                            case when EXAMITEMVALUESNUMBER is null then '1' else '2' end as type,  ");
        stringBuilder.append("                            EXAMITEMVALUESREPORT, ");
        stringBuilder.append("                            F_LABITEMNORMAL ");
        stringBuilder.append("                     from MD_PEISPATIENTEXAMITEM ");
        stringBuilder.append("                     where DEP_ID = '19' ");
        stringBuilder.append("                       and PATIENTCODE = ? ");
        stringBuilder.append("                     union ");
        stringBuilder.append("                     select ID_EXAMFEEITEM, ID_EXAMITEM, '1' as type, EXAMITEMVALUESREPORT, F_LABITEMNORMAL ");
        stringBuilder.append("                     from MD_PEISPATIENTEXAMITEM ");
        stringBuilder.append("                     where DEP_ID <> '19' ");
        stringBuilder.append("                       and PATIENTCODE = ? ");
        stringBuilder.append(") T on T.ID_EXAMITEM = C.EXAM_ID and T.ID_EXAMFEEITEM = C.ITEM_ID ");
        stringBuilder.append("         left join MD_SECTION_RESULT_TWO SRT on SRT.VERDICT_ID = t.ID_EXAMITEM and SRT.CHARGES_ID = T.ID_EXAMFEEITEM ");
        stringBuilder.append("where COMBO_ID in (select ID from MD_CREATECOMBO where JHYS = ? and IS_DELETE = '0' and ZYTJLB = ?)");
        String sql = stringBuilder.toString();
        return jdbcTemplate.query(sql, new ComboexamitemDto(), patientcode, patientcode, JHYS, ZYTJLB);
    }
}