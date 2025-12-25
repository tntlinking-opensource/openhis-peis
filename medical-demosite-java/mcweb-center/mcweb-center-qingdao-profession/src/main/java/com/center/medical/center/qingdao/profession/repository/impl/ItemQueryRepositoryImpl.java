package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.MissingFeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.CheckItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.FeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.NeedFeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.OrdinaryCheckItemDto;
import com.center.medical.center.qingdao.profession.mapper.ItemQueryMapper;
import com.center.medical.center.qingdao.profession.repository.ItemQueryRepository;
import com.center.medical.center.qingdao.profession.service.SpecialTreatmentService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ItemQueryMapper mapper;
    private SpecialTreatmentService specialTreatmentService;

    @Override
    public List<FeeItemDTO> queryFeeItem(String patientCode) {
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("SELECT LISTAGG(CEI.HARM_ID, ',') WITHIN GROUP (ORDER BY CEI.HARM_ID) HARM_IDS, ");
        stringBuilder.append("SELECT GROUP_CONCAT(DISTINCT CEI.HARM_ID) HARM_IDS, ");
        stringBuilder.append("       PI.ID_EXAMFEEITEM                                             ITEM_ID, ");
        stringBuilder.append("       PI.ID_KS                                                      DEP_ID, ");
        stringBuilder.append("       SRM.RUMMAGER_NAME, ");
        stringBuilder.append("       SRM.ZY_CONCLUSIONS, ");
        stringBuilder.append("       0 IS_BUJIAN, ");
        stringBuilder.append("       P.PATIENTCODE ");
        stringBuilder.append("FROM MD_PEISPATIENT P ");
        stringBuilder.append("         INNER JOIN MD_PEISPATIENTFEEITEM PI ON PI.ID_PATIENT = P.PATIENTCODE AND PI.F_EXAMINATED = 1 ");
        stringBuilder.append("         INNER JOIN MD_COMBOEXAMITEM CEI ON CEI.MEDICAL_TYPE = P.MEDICALTYPE  "+specialTreatmentService.treatSql());
        stringBuilder.append("    AND (CEI.HARM_ID = P.JHYS OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID , ',%') OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID) OR ");
        stringBuilder.append("         P.JHYS LIKE CONCAT(CEI.HARM_ID , ',%')  ) ");
        stringBuilder.append("         INNER JOIN MD_SECTION_RESULT_MAIN SRM ON SRM.PATIENTCODE = P.PATIENTCODE AND SRM.DEP_ID = PI.ID_KS ");
        stringBuilder.append("         INNER JOIN SYS_CEN_DEP CD ON CD.DID = PI.ID_KS ");
        stringBuilder.append("WHERE P.PATIENTCODE =  :patientCode ");
        stringBuilder.append("  AND (CD.JKLX NOT IN ('US', 'CR', 'CT', 'MR', 'DX') or CD.JKLX is null ) ");
        stringBuilder.append("GROUP BY PI.ID_EXAMFEEITEM, ");
        stringBuilder.append("         PI.ID_KS, ");
        stringBuilder.append("         SRM.RUMMAGER_NAME, ");
        stringBuilder.append("         SRM.ZY_CONCLUSIONS,P.PATIENTCODE ");

        stringBuilder.append(" ");
        stringBuilder.append("UNION ALL ");
        stringBuilder.append(" ");

//        stringBuilder.append("SELECT LISTAGG(CEI.HARM_ID, ',') WITHIN GROUP (ORDER BY CEI.HARM_ID) HARM_IDS, ");
        stringBuilder.append("SELECT GROUP_CONCAT(DISTINCT CEI.HARM_ID) HARM_IDS, ");
        stringBuilder.append("       PC.ITEM_ID                                                    ITEM_ID, ");
        stringBuilder.append("       PC.DEP_ID                                                     DEP_ID, ");
        stringBuilder.append("       PC.examDoctor                                                 RUMMAGER_NAME, ");
        stringBuilder.append("       PC.EXAMRESULTSUMMARY                                          ZY_CONCLUSIONS, ");
        stringBuilder.append("       0 IS_BUJIAN, ");
        stringBuilder.append("       P.PATIENTCODE ");
        stringBuilder.append("FROM MD_PEISPATIENT P ");
        stringBuilder.append("         INNER JOIN MD_PACS_RESULT PC ON PC.PATIENTCODE = P.PATIENTCODE ");
        stringBuilder.append("         INNER JOIN MD_COMBOEXAMITEM CEI ON CEI.MEDICAL_TYPE = P.MEDICALTYPE AND CEI.ITEM_ID = PC.ITEM_ID ");
        stringBuilder.append("    AND (CEI.HARM_ID = P.JHYS OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID , ',%') OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID) OR ");
        stringBuilder.append("         P.JHYS LIKE CONCAT(CEI.HARM_ID , ',%')   ) ");
        stringBuilder.append("         INNER JOIN MD_SECTION_RESULT_MAIN SRM ");
        stringBuilder.append("                    ON SRM.PATIENTCODE = P.PATIENTCODE AND SRM.DEP_ID = PC.DEP_ID AND SRM.IS_AUDIT = 1 ");
        stringBuilder.append("         INNER JOIN SYS_CEN_DEP CD ON CD.DID = PC.DEP_ID ");
        stringBuilder.append("WHERE P.PATIENTCODE =  :patientCode ");
        stringBuilder.append("  AND (CD.JKLX IN ('US','DR','CR','CT','MR','DX')) ");
        stringBuilder.append("GROUP BY PC.ITEM_ID, ");
        stringBuilder.append("         PC.DEP_ID, ");
        stringBuilder.append("         PC.examDoctor, ");
        stringBuilder.append("         PC.EXAMRESULTSUMMARY,P.PATIENTCODE  ");

        stringBuilder.append(" ");
        stringBuilder.append("UNION ALL ");
        stringBuilder.append(" ");

        //补检
        stringBuilder.append("SELECT group_concat(DISTINCT CEI.HARM_ID) HARM_IDS, ");
        stringBuilder.append("       PI.ID_EXAMFEEITEM                                             ITEM_ID, ");
        stringBuilder.append("       PI.ID_KS                                                      DEP_ID, ");
        stringBuilder.append("       SRM.RUMMAGER_NAME, ");
        stringBuilder.append("       SRM.ZY_CONCLUSIONS, ");
        stringBuilder.append("       1 IS_BUJIAN, ");
        stringBuilder.append("       P.PATIENTCODE ");
        stringBuilder.append("FROM MD_PEISPATIENT P ");
        stringBuilder.append("         INNER JOIN MD_PEISPATIENTFEEITEM PI ON PI.ID_PATIENT = P.PATIENTCODE AND PI.F_EXAMINATED = 1 ");
        stringBuilder.append("         INNER JOIN MD_COMBOEXAMITEM CEI ON CEI.MEDICAL_TYPE = P.MEDICALTYPE  "+specialTreatmentService.treatSql());
        stringBuilder.append("    AND (CEI.HARM_ID = P.JHYS OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID , ',%') OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID) OR ");
        stringBuilder.append("         P.JHYS LIKE CONCAT(CEI.HARM_ID , ',%')  ) ");
        stringBuilder.append("         INNER JOIN MD_SECTION_RESULT_MAIN SRM ON SRM.PATIENTCODE = P.PATIENTCODE AND SRM.DEP_ID = PI.ID_KS ");
        stringBuilder.append("         INNER JOIN sys_department CD ON CD.id = PI.ID_KS ");
        stringBuilder.append("WHERE P.INSURANCENO =  :patientCode AND P.ZYTJZT >= 7 ");
        stringBuilder.append("  AND (CD.JKLX NOT IN ('US', 'CR', 'CT', 'MR', 'DX') or CD.JKLX is null ) ");
        stringBuilder.append("GROUP BY PI.ID_EXAMFEEITEM, ");
        stringBuilder.append("         PI.ID_KS, ");
        stringBuilder.append("         SRM.RUMMAGER_NAME, ");
        stringBuilder.append("         SRM.ZY_CONCLUSIONS,P.PATIENTCODE  ");

        stringBuilder.append(" ");
        stringBuilder.append("UNION ALL ");
        stringBuilder.append(" ");

        stringBuilder.append("SELECT group_concat(DISTINCT CEI.HARM_ID) HARM_IDS, ");
        stringBuilder.append("       PC.ITEM_ID                                                    ITEM_ID, ");
        stringBuilder.append("       PC.DEP_ID                                                     DEP_ID, ");
        stringBuilder.append("       PC.examDoctor                                                 RUMMAGER_NAME, ");
        stringBuilder.append("       PC.EXAMRESULTSUMMARY                                          ZY_CONCLUSIONS, ");
        stringBuilder.append("       1 IS_BUJIAN, ");
        stringBuilder.append("       P.PATIENTCODE ");
        stringBuilder.append("FROM MD_PEISPATIENT P ");
        stringBuilder.append("         INNER JOIN MD_PACS_RESULT PC ON PC.PATIENTCODE = P.PATIENTCODE ");
        stringBuilder.append("         INNER JOIN MD_COMBOEXAMITEM CEI ON CEI.MEDICAL_TYPE = P.MEDICALTYPE AND CEI.ITEM_ID = PC.ITEM_ID ");
        stringBuilder.append("    AND (CEI.HARM_ID = P.JHYS OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID , ',%') OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID) OR ");
        stringBuilder.append("         P.JHYS LIKE CONCAT(CEI.HARM_ID , ',%')  ) ");
        stringBuilder.append("         INNER JOIN MD_SECTION_RESULT_MAIN SRM ");
        stringBuilder.append("                    ON SRM.PATIENTCODE = P.PATIENTCODE AND SRM.DEP_ID = PC.DEP_ID AND SRM.IS_AUDIT = 1 ");
        stringBuilder.append("         INNER JOIN sys_department CD ON CD.id = PC.DEP_ID ");
        stringBuilder.append("WHERE P.INSURANCENO =  :patientCode  AND P.ZYTJZT >= 7  ");
        stringBuilder.append("  AND (CD.JKLX IN ('US','DR','CR','CT','MR','DX')) ");
        stringBuilder.append("GROUP BY PC.ITEM_ID, ");
        stringBuilder.append("         PC.DEP_ID, ");
        stringBuilder.append("         PC.examDoctor, ");
        stringBuilder.append("         PC.EXAMRESULTSUMMARY,P.PATIENTCODE  ");



        String sql = stringBuilder.toString();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("patientCode", patientCode);
        return namedParameterJdbcTemplate.query(sql, paramMap, (resultSet, i) -> {
            FeeItemDTO feeItemDTO = new FeeItemDTO();
            feeItemDTO.setHarmIds(resultSet.getString("HARM_IDS"));
            feeItemDTO.setItemId(resultSet.getString("ITEM_ID"));
            feeItemDTO.setDepId(resultSet.getString("DEP_ID"));
            feeItemDTO.setRummagerName(resultSet.getString("RUMMAGER_NAME"));
            feeItemDTO.setZyConclusions(resultSet.getString("ZY_CONCLUSIONS"));
            feeItemDTO.setOriginalItemId(resultSet.getString("ITEM_ID"));
            feeItemDTO.setIsBujian(resultSet.getInt("IS_BUJIAN"));
            feeItemDTO.setPatientcode(resultSet.getString("PATIENTCODE"));
            return feeItemDTO;
        });
    }

    @Override
    public List<FeeItemDTO> queryFeeItem2(String patientCode) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT GROUP_CONCAT(DISTINCT CEI.HARM_ID) HARM_IDS, " +
                "       PI.ID_EXAMFEEITEM               ITEM_ID " +
                "        , " +
                "       PI.ID_KS                        DEP_ID " +
                "FROM MD_PEISPATIENT P " +
                "         INNER JOIN MD_PEISPATIENTFEEITEM PI ON PI.ID_PATIENT = P.PATIENTCODE AND PI.F_EXAMINATED = 1 " +
                "         INNER JOIN MD_COMBOEXAMITEM CEI ON CEI.MEDICAL_TYPE = P.MEDICALTYPE AND CEI.ITEM_ID = PI.ID_EXAMFEEITEM " +
                "    AND (CEI.HARM_ID = P.JHYS OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID , ',%') OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID) OR " +
                "         P.JHYS LIKE CONCAT(CEI.HARM_ID , ',%')  ) " +
                "         INNER JOIN MD_SECTION_RESULT_MAIN SRM ON SRM.PATIENTCODE = P.PATIENTCODE AND SRM.DEP_ID = PI.ID_KS " +
                "         INNER JOIN SYS_CEN_DEP CD ON CD.DID = PI.ID_KS " +
                "WHERE P.PATIENTCODE = :patientCode " +
                "  AND (CD.JKLX NOT IN ('US', 'CR', 'CT', 'MR', 'DX') or CD.JKLX is null) " +
                "GROUP BY PI.ID_EXAMFEEITEM " +
                "        , " +
                "         PI.ID_KS " +
                " " +
                "UNION ALL " +
                " " +
                "SELECT GROUP_CONCAT(DISTINCT CEI.HARM_ID) HARM_IDS, " +
                "       PC.ITEM_ID                      ITEM_ID " +
                "        , " +
                "       PC.DEP_ID                       DEP_ID " +
                "FROM MD_PEISPATIENT P " +
                "         INNER JOIN MD_PACS_RESULT PC ON PC.PATIENTCODE = P.PATIENTCODE " +
                "         INNER JOIN MD_COMBOEXAMITEM CEI ON CEI.MEDICAL_TYPE = P.MEDICALTYPE AND CEI.ITEM_ID = PC.ITEM_ID " +
                "    AND (CEI.HARM_ID = P.JHYS OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID , ',%') OR P.JHYS LIKE CONCAT('%,' , CEI.HARM_ID) OR " +
                "         P.JHYS LIKE CONCAT(CEI.HARM_ID , ',%') ) " +
                "         INNER JOIN MD_SECTION_RESULT_MAIN SRM " +
                "                    ON SRM.PATIENTCODE = P.PATIENTCODE AND SRM.DEP_ID = PC.DEP_ID AND SRM.IS_AUDIT = 1 " +
                "         INNER JOIN SYS_CEN_DEP CD ON CD.DID = PC.DEP_ID " +
                "WHERE P.PATIENTCODE = :patientCode " +
                "  AND (CD.JKLX IN ('US', 'DR', 'CR', 'CT', 'MR', 'DX')) " +
                "GROUP BY PC.ITEM_ID " +
                "       , PC.DEP_ID");
        String sql = stringBuilder.toString();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("patientCode", patientCode);
        ArrayList<FeeItemDTO> feeItemDTOS = new ArrayList<>();
        List<FeeItemDTO> query = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<FeeItemDTO>() {
            @Override
            public FeeItemDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                FeeItemDTO feeItemDTO = new FeeItemDTO();
                feeItemDTO.setHarmIds(resultSet.getString("HARM_IDS"));
                feeItemDTO.setItemId(resultSet.getString("ITEM_ID"));
                feeItemDTO.setDepId(resultSet.getString("DEP_ID"));
//                feeItemDTO.setRummagerName(resultSet.getString("RUMMAGER_NAME"));
//                feeItemDTO.setZyConclusions(resultSet.getString("ZY_CONCLUSIONS"));
                return feeItemDTO;
            }
        });
        feeItemDTOS.addAll(query);
        feeItemDTOS.addAll(queryBlood(patientCode));
        return feeItemDTOS;

    }

    private Collection<? extends FeeItemDTO> queryBlood(String patientCode) {
        return new ArrayList<>();
    }

    @Override
    public List<CheckItemDTO> queryCheckItemDTO(String patientCode, String itemId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select distinct * ");
        stringBuilder.append("from (SELECT PEI.PATIENTCODE                                                      EXAM_NUM, ");
        stringBuilder.append("             PEI.ID_ExamFeeItem                                                   EXAM_ITEM_PCODE, ");
        stringBuilder.append("             PEI.ExamItem_Name_R                                                  HS_EXAM_ITEM_NAME, ");
        stringBuilder.append("             PEI.ID_ExamItem                                                      HS_EXAM_ITEM_CODE, ");
        stringBuilder.append("             (CASE WHEN PEI.examItemValuesNumber IS NULL THEN '02' ELSE '01' END) EXAM_RESULT_TYPE, ");
        stringBuilder.append("             PEI.EXAMITEMVALUESREPORT                                             EXAM_RESULT, ");
        stringBuilder.append("             PEI.UNITS                                                            EXAM_ITEM_UNIT_CODE, ");
        stringBuilder.append("             NULL                                                                 REFERENCE_RANGE_MIN, ");
        stringBuilder.append("             NULL                                                                 REFERENCE_RANGE_MAX, ");
        stringBuilder.append("             ( ");
        stringBuilder.append("                 CASE ");
        stringBuilder.append("                     WHEN PEI.STATUS = '↑' THEN 'T' ");
        stringBuilder.append("                     WHEN PEI.STATUS = '↓' THEN 'T' ");
        stringBuilder.append("                     WHEN INSTR(PEI.examItemValuesShort, '') > 0 THEN 'T' ");
        stringBuilder.append("                     WHEN INSTR(PEI.examItemValuesShort, '阳性') > 0 THEN 'T' ");
        stringBuilder.append("                     WHEN PEI.ID_ExamItem IN ('1215', '917', '918', '919') AND PEI.examItemValuesShort != '阴性' AND ");
        stringBuilder.append("                          PEI.examItemValuesShort != '未见' ");
        stringBuilder.append("                         AND PEI.examItemValuesShort IS NOT NULL THEN 'T' ");
        stringBuilder.append("                     ELSE 'F' END ");
        stringBuilder.append("                 )                                                                ABNORMAL ");
        stringBuilder.append("      FROM MD_PEISPATIENTEXAMITEM PEI ");
        stringBuilder.append("               INNER JOIN MD_PEISPATIENTFEEITEM PI ");
        stringBuilder.append("                          ON PI.ID_PATIENT = PEI.PATIENTCODE AND PI.ID_EXAMFEEITEM = PEI.ID_ExamFeeItem AND ");
        stringBuilder.append("                             PI.CHANGE_ITEM = 0 ");
        stringBuilder.append("      WHERE PEI.PATIENTCODE =  :patientCode ");
        stringBuilder.append(" ");
        stringBuilder.append("      UNION ALL ");
        stringBuilder.append(" ");
        stringBuilder.append("      SELECT DS.PATIENTCODE                      EXAM_NUM, ");
        stringBuilder.append("             DS.FEE_ID                           EXAM_ITEM_PCODE, ");
        stringBuilder.append("             E.EXAMITEM_NAMEPRN                  HS_EXAM_ITEM_NAME, ");
        stringBuilder.append("             E.ID                                HS_EXAM_ITEM_CODE, ");
        stringBuilder.append("             '02'                                EXAM_RESULT_TYPE, ");
        stringBuilder.append("             DS.SIGN_LIST EXAM_RESULT, ");
        stringBuilder.append("             NULL                                EXAM_ITEM_UNIT_CODE, ");
        stringBuilder.append("             NULL                                REFERENCE_RANGE_MIN, ");
        stringBuilder.append("             NULL                                REFERENCE_RANGE_MAX, ");
        stringBuilder.append("             (CASE ");
        stringBuilder.append("                  WHEN EXISTS( ");
        stringBuilder.append("                          SELECT 1 ");
        stringBuilder.append("                          FROM MD_SECTION_RESULT_TWO T ");
        stringBuilder.append("                          WHERE T.PATIENTCODE = DS.PATIENTCODE ");
        stringBuilder.append("                            AND T.VERDICT_ID = DS.ITEM_ID ");
        stringBuilder.append("                            AND T.CHARGES_ID = DS.FEE_ID ");
        stringBuilder.append("                            AND T.POSISTIVE = 1 ");
        stringBuilder.append("                      ) THEN 'T' ");
        stringBuilder.append("                  ELSE 'F' END)                  ABNORMAL ");
        stringBuilder.append("      FROM MD_DESCRIBE DS ");
        stringBuilder.append("               INNER JOIN MD_PEISPATIENTFEEITEM PI ");
        stringBuilder.append("                          ON PI.ID_PATIENT = DS.PATIENTCODE AND PI.ID_EXAMFEEITEM = DS.FEE_ID AND PI.CHANGE_ITEM = 0 ");
        stringBuilder.append("               INNER JOIN MD_BASEXAMLTEM E ON E.ID = DS.ITEM_ID ");
        stringBuilder.append("               INNER JOIN SYS_CEN_DEP QCD ON QCD.DID = DS.DEP_ID ");
        stringBuilder.append("      WHERE DS.PATIENTCODE =  :patientCode ");
        stringBuilder.append("        AND DS.DEP_ID != '19'  AND DS.DEP_ID!='20' ");
        stringBuilder.append("        AND (QCD.JKLX NOT IN ('US', 'CR', 'CT', 'MR', 'DX') or QCD.JKLX is null )");
        stringBuilder.append(" ");

        //一般检查的describe.fee_id有问题，存的检查项目id
        stringBuilder.append("      UNION ALL ");
        stringBuilder.append(" ");
        stringBuilder.append("      SELECT distinct DS.PATIENTCODE                      EXAM_NUM, ");
        stringBuilder.append("              'Z005'                          EXAM_ITEM_PCODE, ");//存在同时有一般检查职业和血压脉搏职业的，这两个项目都有收缩压舒张压
        stringBuilder.append("             E.EXAMITEM_NAMEPRN                  HS_EXAM_ITEM_NAME, ");
        stringBuilder.append("             E.ID                                HS_EXAM_ITEM_CODE, ");
        stringBuilder.append("             '02'                                EXAM_RESULT_TYPE, ");
        stringBuilder.append("             SUBSTRING(DS.SIGN_LIST, 1, 4000) AS EXAM_RESULT, ");
        stringBuilder.append("             NULL                                EXAM_ITEM_UNIT_CODE, ");
        stringBuilder.append("             NULL                                REFERENCE_RANGE_MIN, ");
        stringBuilder.append("             NULL                                REFERENCE_RANGE_MAX, ");
        stringBuilder.append("             (CASE ");
        stringBuilder.append("                  WHEN EXISTS( ");
        stringBuilder.append("                          SELECT 1 ");
        stringBuilder.append("                          FROM MD_SECTION_RESULT_TWO T ");
        stringBuilder.append("                          WHERE T.PATIENTCODE = DS.PATIENTCODE ");
        stringBuilder.append("                            AND T.VERDICT_ID = DS.ITEM_ID ");
        stringBuilder.append("                            AND T.CHARGES_ID = IC.CHARGE_ID  ");
        stringBuilder.append("                            AND T.POSISTIVE = 1 ");
        stringBuilder.append("                      ) THEN 'T' ");
        stringBuilder.append("                  ELSE 'F' END)                  ABNORMAL ");
        stringBuilder.append("      FROM MD_DESCRIBE DS ");
        stringBuilder.append("               INNER JOIN MD_BASEXAMLTEM E ON E.ID = DS.ITEM_ID ");
        stringBuilder.append("               INNER JOIN MD_INSPECT_CHARGE IC ON IC.INSPECT_ID=E.ID                         ");
        stringBuilder.append("               INNER JOIN MD_PEISPATIENTFEEITEM PI ");
        stringBuilder.append("                          ON PI.ID_PATIENT = DS.PATIENTCODE AND PI.ID_EXAMFEEITEM = IC.CHARGE_ID AND PI.CHANGE_ITEM = 0 ");
        stringBuilder.append("               INNER JOIN sys_department QCD ON QCD.id = DS.DEP_ID ");
        stringBuilder.append("      WHERE DS.PATIENTCODE =  :patientCode ");
        stringBuilder.append("        AND DS.DEP_ID = '20' ");
        stringBuilder.append("        AND (QCD.JKLX NOT IN ('US', 'CR', 'CT', 'MR', 'DX') or QCD.JKLX is null )");
        stringBuilder.append(" ");

        stringBuilder.append("      UNION ALL ");
        stringBuilder.append(" ");
        stringBuilder.append("      SELECT PR.PATIENTCODE       EXAM_NUM, ");
        stringBuilder.append("             PR.ITEM_ID           EXAM_ITEM_PCODE, ");
        stringBuilder.append("             E.EXAMITEM_NAMEPRN   HS_EXAM_ITEM_NAME, ");
        stringBuilder.append("             E.ID                 HS_EXAM_ITEM_CODE, ");
        stringBuilder.append("             '02'                 EXAM_RESULT_TYPE, ");
        stringBuilder.append("             PR.EXAMRESULTSUMMARY EXAM_RESULT, ");
        stringBuilder.append("             NULL                 EXAM_ITEM_UNIT_CODE, ");
        stringBuilder.append("             NULL                 REFERENCE_RANGE_MIN, ");
        stringBuilder.append("             NULL                 REFERENCE_RANGE_MAX, ");
        stringBuilder.append("             (CASE ");
        stringBuilder.append("                  WHEN EXISTS( ");
        stringBuilder.append("                          SELECT 1 ");
        stringBuilder.append("                          FROM MD_PACS_SECTION_RESULT_TWO T ");
        stringBuilder.append("                          WHERE T.PATIENTCODE = PR.PATIENTCODE ");
        stringBuilder.append("                            AND T.CHARGES_ID = PR.PACS_ITEM_ID ");
        stringBuilder.append("                            AND T.POSISTIVE = 1 ");
        stringBuilder.append("                      ) THEN 'T' ");
        stringBuilder.append("                  ELSE 'F' END)   ABNORMAL ");
        stringBuilder.append("      FROM MD_PACS_RESULT PR ");
        stringBuilder.append("               INNER JOIN MD_ITEMS I ON I.ID = PR.ITEM_ID ");
        stringBuilder.append("               INNER JOIN MD_INSPECT_CHARGE IC ON IC.CHARGE_ID = I.ID ");
        stringBuilder.append("               INNER JOIN MD_BASEXAMLTEM E ON E.ID = IC.INSPECT_ID ");
        stringBuilder.append("               INNER JOIN MD_PEISPATIENTFEEITEM PI ");
        stringBuilder.append("                          ON PI.ID_PATIENT = PR.PATIENTCODE AND PI.ID_EXAMFEEITEM = PR.ITEM_ID AND PI.CHANGE_ITEM = 0 ");
        stringBuilder.append("      WHERE PR.PATIENTCODE =  :patientCode) T   ");
        stringBuilder.append("where EXAM_ITEM_PCODE in (:EXAM_ITEM_PCODE) ");
        stringBuilder.append(" ");
        String sql = stringBuilder.toString();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("EXAM_ITEM_PCODE", specialTreatmentService.getItemIds(itemId));
        paramMap.put("patientCode", patientCode);
        return namedParameterJdbcTemplate.query(sql, paramMap, new CheckItemDTO());
    }


    @Override
    public List<OrdinaryCheckItemDto> queryOrdinaryCheckItem(String patientCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select PATIENTCODE as EXAM_NUM, ");
        stringBuilder.append("       'Z005'      as EXAM_ITEM_PCODE, ");
        stringBuilder.append("       ITEM_NAME   as HS_EXAM_ITEM_NAME, ");
        stringBuilder.append("       ITEM_ID     as HS_EXAM_ITEM_CODE, ");
        stringBuilder.append("       '01'        as EXAM_RESULT_TYPE, ");
        stringBuilder.append("       SIGN_LIST   as EXAM_RESULT, ");
        stringBuilder.append("       null        as EXAM_ITEM_UNIT_CODE, ");
        stringBuilder.append("       null        as REFERENCE_RANGE_MIN, ");
        stringBuilder.append("       null        as REFERENCE_RANGE_MAX, ");
        stringBuilder.append("       null        as ABNORMAL ");
        stringBuilder.append("from MD_DESCRIBE ");
        stringBuilder.append("where (FEE_NAME = '一般检查' OR FEE_NAME='血压脉搏') ");
        stringBuilder.append("  and PATIENTCODE = :patientCode");
        String sql = stringBuilder.toString();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("patientCode", patientCode);
        return namedParameterJdbcTemplate.query(sql, hashMap, new OrdinaryCheckItemDto());
    }

    @Override
    public List<NeedFeeItemDTO> queryNeedFeeItem(String medicaltype, String harmIds) {
        String sql = "select ITEM_ID, EXAM_ID,HARM_ID,COMBO_ID from MD_COMBOEXAMITEM where MEDICAL_TYPE = :medicalType   and HARM_ID in (:harmId)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("medicalType", medicaltype);
        paramMap.put("harmId", Arrays.asList(harmIds.split(",")));
        return namedParameterJdbcTemplate.query(sql, paramMap, (resultSet, i) -> {
            NeedFeeItemDTO needFeeItemDTO = new NeedFeeItemDTO();
            needFeeItemDTO.setItemId(resultSet.getString("ITEM_ID"));
            needFeeItemDTO.setExamId(resultSet.getString("EXAM_ID"));
            needFeeItemDTO.setHarmId(resultSet.getString("HARM_ID"));
            needFeeItemDTO.setComboId(resultSet.getString("COMBO_ID"));
            return needFeeItemDTO;
        });
    }

    @Override
    public List<CheckItemDTO> queryNotCheckItemDTO(List<String> itemId) {
        String sql = "select EXAMITEM_NAME,ID " +
                "from md_BASEXAMLTEM " +
                "where ID in (:ids)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ids", itemId);
        return namedParameterJdbcTemplate.query(sql, paramMap, getRowMapper());
    }

    @Override
    public List<MissingFeeItemDTO> queryMissingFeeItemsDTOS(String patientcode, List<String> list) {
        String sql = "SELECT ID, ID_EXAMFEEITEM, EXAMFEEITEM_NAME " +
                "FROM MD_PEISPATIENTFEEITEM " +
                "WHERE ID_PATIENT = :patientcode " +
                "  AND EXAMFEEITEM_NAME IN (:list)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("patientcode", patientcode);
        paramMap.put("list", list);
        return namedParameterJdbcTemplate.queryForList(sql, paramMap, MissingFeeItemDTO.class);
    }

    @Override
    public Set<String> queryNeedFeeItemMust(Set<String> comboIds) {
        String sql = "SELECT SFXMID FROM MD_comboanditem WHERE TCID IN (:tcid) AND sfbj=1 AND is_delete=0";
        Map<String, Object> map = new HashMap<>();
        map.put("tcid", comboIds);
        List<String> strings = namedParameterJdbcTemplate.queryForList(sql, map, String.class);
        return new HashSet<>(strings);
    }

    private RowMapper<CheckItemDTO> getRowMapper() {
        return (resultSet, i) -> {
            CheckItemDTO checkItemDTO = new CheckItemDTO();
//            checkItemDTO.setReferenceRangeMin("无");
//            checkItemDTO.setReferenceRangeMax("无");
            checkItemDTO.setAbnormal("0");
            checkItemDTO.setExamItemUnitCode("%");
            checkItemDTO.setExamResult("未检");
            checkItemDTO.setExamResultType("02");
            checkItemDTO.setHsExamItemName(resultSet.getString("EXAMITEM_NAME"));
            checkItemDTO.setHsExamItemCode(resultSet.getString("ID"));
            return checkItemDTO;
        };
    }


}
