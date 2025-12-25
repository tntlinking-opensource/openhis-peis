package com.center.medical.datamove.admin.service.impl;

import com.center.medical.datamove.admin.bean.dto.IndividualDto;
import com.center.medical.datamove.admin.service.DatamoveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-06-17 11:45
 * @description:
 */
@Slf4j
@Service("datamovesService")
@RequiredArgsConstructor
public class DatamoveServiceImpl implements DatamoveService {

//    private final OrCreateorderService orCreateorderService;
//    private final MdCreateorderService mdCreateorderService;
//    private final MapperFacade mapperFacade;
//    private final OrSellcustomerService orSellcustomerService;
//    private final CrmSellcustomerService crmSellcustomerService;
//    private final OrOrderandfzxService orOrderandfzxService;
//    private final MdOrderandfzxService mdOrderandfzxService;
//    private final OrSellpactService orSellpactService;
//    private final CrmSellpactService crmSellpactService;
//    private final OrOrderandcomboService orOrderandcomboService;
//    private final MdOrderandcomboService mdOrderandcomboService;
//    private final OrCreatemealService orCreatemealService;
//    private final MdCreatemealService mdCreatemealService;
//    private final OrCreatecomboService orCreatecomboService;
//    private final MdCreatecomboService mdCreatecomboService;
//    private final OrComboandfzxService orComboandfzxService;
//    private final OrComboanditemService comboanditemService;
//    private final MdComboandfzxService mdComboandfzxService;
//    private final MdComboanditemService mdComboanditemService;
//    private final OrMealanditemService orMealanditemService;
//    private final OrMealandfzxService orMealandfzxService;
//    private final MdMealanditemService mdMealanditemService;
//    private final MdMealandfzxService mdMealandfzxService;
//    private final JdbcTemplate jdbcTemplate;
//    private final OrPeisorgreservationService orPeisorgreservationService;
//    private final MdPeisorgreservationService mdPeisorgreservationService;
//    private final OrPeisorgreservationgroupService orPeisorgreservationgroupService;
//    private final MdPeisorgreservationgroupService mdPeisorgreservationgroupService;
//    private final OrPeispatientService orPeispatientService;
//    private final MdPeispatientService mdPeispatientService;
//    private final OrPeispatientfeeitemService orPeispatientfeeitemService;
//    private final MdPeispatientfeeitemService mdPeispatientfeeitemService;
//    private final OrPeispatientChargeMainService orPeispatientChargeMainService;
//    private final MdPeispatientChargeMainService mdPeispatientChargeMainService;


    /**
     * 开始迁移
     *
     * @return
     */
    @Override
    public Boolean move() throws SQLException {
        // 设置Oracle数据库连接信息
        String oracleUrl = "jdbc:oracle:thin:@150.0.1.249:1521:orcl";
        String oracleUsername = "medical";
        String oraclePassword = "medical";
//        String oracleUrl = "jdbc:oracle:thin:@218.201.98.189:1521:orcl";
//        String oracleUsername = "medical";
//        String oraclePassword = "medical";

        // 设置MySQL数据库连接信息
//        String mysqlUrl = "jdbc:mysql://XXX.XXX.XXX.XXX:3306/medical_his";
//        String mysqlUsername = "medical_his";
//        String mysqlPassword = "zk@0823";
        String mysqlUrl = "jdbc:mysql://150.0.1.251:38306/medical_prod";
        String mysqlUsername = "medical_prod";
        String mysqlPassword = "zkgj@0603";
        try {
            // 连接到Oracle数据库
            Connection oracleConnection = DriverManager.getConnection(oracleUrl, oracleUsername, oraclePassword);

            // 连接到MySQL数据库
            Connection mysqlConnection = DriverManager.getConnection(mysqlUrl, mysqlUsername, mysqlPassword);
            Statement mysqlStatement = mysqlConnection.createStatement();
            // 关闭自动提交事务，改为手动提交
//            mysqlConnection.setAutoCommit(
            // 获取对应的数据库表
            List<String[]> tableNameList = findTableNameList();
            for (String[] table : tableNameList) {
                String oracleQuery = "SELECT * FROM " + table[0];

                // 执行查询
                Statement oracleStatement = oracleConnection.createStatement();
                ResultSet oracleResultSet = oracleStatement.executeQuery(oracleQuery);

                // 删除mysql表数据
                String delete = "DELETE FROM " + table[1];
                int update = mysqlStatement.executeUpdate(delete);
                log.info("开始删除表{}，删除{}行", table[1], update);

                // 遍历Oracle结果集
                int batchSize = 0;
                StringBuilder mysqlInsertStatement = new StringBuilder("INSERT INTO " + table[1] + " (");
                ResultSetMetaData oracleMetadata = oracleResultSet.getMetaData();
                int columnCount = oracleMetadata.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    mysqlInsertStatement.append("`" + oracleMetadata.getColumnName(i) + "`");
                    if (i < columnCount) {
                        mysqlInsertStatement.append(", ");
                    }
                }

                mysqlInsertStatement.append(") VALUES (");
                for (int i = 1; i <= columnCount; i++) {
                    mysqlInsertStatement.append("?");
                    if (i < columnCount) {
                        mysqlInsertStatement.append(", ");
                    }
                }
                mysqlInsertStatement.append(")");

                PreparedStatement mysqlPreparedStatement = mysqlConnection.prepareStatement(mysqlInsertStatement.toString());

                while (oracleResultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String columnValue = oracleResultSet.getString(i);
                        mysqlPreparedStatement.setString(i, columnValue);
                    }
                    // 添加到批处理中
                    mysqlPreparedStatement.addBatch();
                    batchSize++;

                    // 每1000条记录执行一次批量插入
                    if (batchSize % 1000 == 0) {
                        mysqlPreparedStatement.executeBatch();
//                        mysqlConnection.commit();
                        batchSize = 0;
                    }
                }

                // 执行最后一次批量插入
                if (batchSize > 0) {
                    mysqlPreparedStatement.executeBatch();
                }

                log.info("表:{},导入成功", table[1]);
//                oracleResultSet.close();
//                oracleStatement.close();
            }

            // 处理个别的表
            individuallyQueried(oracleConnection, mysqlConnection);
//             关闭Oracle连接和资源
//            oracleConnection.close();

            // 关闭MySQL连接和资源
//            mysqlStatement.close();
//            mysqlConnection.close();

        } catch (Exception throwables) {
            log.info(throwables.toString());
        }


        return Boolean.TRUE;

    }


    /**
     * 初始化需要删除的表
     *
     * @return
     */
    private List<String> deleteTable() {
        List<String> list = Arrays.asList("crm_clientcommon", "crm_customer_operate_history", "crm_customer_transfer",
                "crm_customercommunicate", "crm_customerfollow", "crm_order_conflict",
                "crm_order_plan", "crm_order_plan_linker", "crm_receiveandsell",
                "crm_sell_outside", "crm_sell_remind", "crm_sellcustomer", "crm_sellpact",
                "crm_selltarget", "crm_teamremind", "kd_remittance", "md_advance_visit", "md_advance_visit_write",
                "md_attach_file", "md_attachment", "md_attachment_config", "md_attachment_group",
                "md_ball_check_report", "md_batch_sms_record", "md_bk_patient", "md_bk_patientfeeitem",
                "md_card", "md_card_member_medical", "md_card_payway", "md_card_recheck_record", "md_chest",
                "md_comments_progessional", "md_compare_report", "md_consulation", "md_create_order_qtxz", "md_createmeal",
                "md_createorder", "md_current_queue_info", "md_cust_feedback", "md_danager_object", "md_describe",
                "md_dining_record", "md_drugstore_prescribe", "md_dw_harm",
                "md_electro_audiometer", "md_fail_total_visit", "md_family_card_charge",
                "md_file_change_record", "md_financeinput", "md_financial_reporting",
                "md_fx_completion", "md_fx_detection", "md_fx_detectionzy", "md_fx_harm",
                "md_fx_itemscheck", "md_fx_negative", "md_fx_personnelview", "md_fx_positive",
                "md_fx_review_info", "md_fx_reviewsituation", "md_fx_summary", "md_group_and_fzx",
                "md_group_balance", "md_group_review_notice", "md_group_review_notice_patient", "md_handle_new_projects",
                "md_inspect_report", "md_laboratory_result", "md_leadertarget", "md_limit_operation",
                "md_lung", "md_mealandfzx", "md_mealanditem", "md_memberbirthdat",
                "md_memberintegral", "md_monthtarget", "md_notifier", "md_notify_sms_exam",
                "md_notify_sms_visit", "md_nuclein", "md_operate_log", "md_orderandcombo",
                "md_orderandfzx", "md_ordersummary", "md_other_report", "md_outside_charge_item",
                "md_outside_checkin", "md_outside_hand", "md_outside_main", "md_outside_pictrue",
                "md_pa_peissortexam", "md_pacs_peispatient", "md_pacs_peispatientfeeitem", "md_pacs_result",
                "md_pacs_section_result_main", "md_pacs_section_result_two", "md_patient_users", "md_patienttype",
                "md_peis_ol", "md_peis_questionnaire", "md_peis_questionnaire_second", "md_peis_reser_payway",
                "md_peis_state", "md_peisorgreservation", "md_peisorgreservationgroup", "md_peisorgreservationreceipt",
                "md_peispatient", "md_peispatient_and_fzx", "md_peispatient_charge_main", "md_peispatient_charge_other",
                "md_peispatient_charge_record", "md_peispatient_consultation", "md_peispatient_consultation_pic",
                "md_peispatient_photo", "md_peispatient_reservation_charge", "md_peispatientarchive", "md_peispatientcharge",
                "md_peispatientexamitem", "md_peispatientfeeitem", "md_peispatienthistory", "md_peissortexam", "md_pricture",
                "md_pufftube", "md_report", "md_report_content", "md_report_url", "md_reservation", "md_reservation_1",
                "md_reservation_return_visit", "md_reservation_setting", "md_review", "md_review_project", "md_riskclient",
                "md_riskclientcon", "md_sales_target_statistic", "md_sample_connect", "md_sample_delivery", "md_sample_person",
                "md_satisfaction", "md_savefilepath", "md_section_and_remind", "md_section_remind", "md_section_result_main",
                "md_section_result_plan", "md_section_result_two", "md_section_total", "md_sh_report", "md_sms_record",
                "md_sortexam_limit", "md_sync_data", "md_sync_data_log", "md_tb_patient", "md_temp_feeitem",
                "md_temporaryqueue", "md_temporaryqueuetest", "md_tjbb_bmi", "md_tjbb_gmd", "md_tjbb_xtjc",
                "md_tjbb_xyjc", "md_tjbb_xzjc", "md_tjdw_branch", "md_tjreg", "md_total_doctor", "md_trade_record",
                "md_user", "md_user_level_bind", "md_user_mapping", "md_user_mapping_sys", "md_user_saleer",
                "md_userauthcode", "md_utside_hand", "md_vation_and_fzx", "md_visit_main", "md_visit_write",
                "md_wz_callback", "md_wz_grxx", "md_wz_latest_rummager", "md_wz_life", "md_wz_symptom",
                "md_wz_tjdwxx", "md_wz_tjdwxx_mx", "md_wz_tjrecord", "md_wz_zybs", "md_wz_zys", "md_wz_zys_whys");
        return list;

    }

    /**
     * 处理个别的表
     *
     * @param oracleConnection
     * @param mysqlConnection
     */
    private void individuallyQueried(Connection oracleConnection, Connection mysqlConnection) throws SQLException {
        //获取个别表需处理的数据
        List<IndividualDto> list = getIndividuallyData();
        Statement mysqlStatement = mysqlConnection.createStatement();
        for (IndividualDto dto : list) {
            String oracleQuery = "SELECT " + String.join(",", dto.getOracleField()) + " FROM " + dto.getOracleTable();
            // 执行查询
            Statement oracleStatement = oracleConnection.createStatement();
            ResultSet oracleResultSet = oracleStatement.executeQuery(oracleQuery);
            // 获取Oracle结果集的元数据
            ResultSetMetaData oracleMetadata = oracleResultSet.getMetaData();
            // 获取Oracle结果集的列数
            int columnCount = oracleMetadata.getColumnCount();
            // 遍历Oracle结果集
            //删除mysql表元数据
            String delete = "DELETE FROM " + dto.getMysqlTable();
            int update = mysqlStatement.executeUpdate(delete);
            log.info("开始删除表{}，删除{}行", dto.getMysqlTable(), update);

            log.info("开始删除，删除" + update + "行");
            while (oracleResultSet.next()) {
                // 创建MySQL插入语句
                StringBuilder mysqlInsertStatement = new StringBuilder("INSERT INTO " + dto.getMysqlTable() + " (");
                // 构建MySQL插入语句的列名部分
                List<String> columnNames = new ArrayList<>();
                List<String> mysqlField = dto.getMysqlField();
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = oracleResultSet.getString(i);
                    String columnName = mysqlField.get(i - 1);
                    if (columnValue != null && !("\"NULL\"".equals(columnValue) || "\"null\"".equals(columnValue))) {
                        columnNames.add("`" + columnName.toLowerCase() + "`");
                    }
                }
                mysqlInsertStatement.append(String.join(", ", columnNames));
                mysqlInsertStatement.append(") VALUES (");
                // 构建MySQL插入语句的值部分
                List<String> columnValues = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = oracleResultSet.getString(i);
                    if (columnValue != null && !("\"NULL\"".equals(columnValue) || "\"null\"".equals(columnValue))) {
                        columnValues.add("?");
                    }
                }
                mysqlInsertStatement.append(String.join(", ", columnValues));
                mysqlInsertStatement.append(")");

                // 使用PreparedStatement执行MySQL插入语句
                PreparedStatement mysqlPreparedStatement = mysqlConnection.prepareStatement(mysqlInsertStatement.toString());
                int parameterIndex = 1;
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = oracleMetadata.getColumnName(i);
                    String columnValue = oracleResultSet.getString(i);
                    if (columnValue != null && !("\"NULL\"".equals(columnValue) || "\"null\"".equals(columnValue))) {
                        //本次沟通方式：0.电话 1.QQ 2.面对面 3.其它
                        if (columnName.equals("BCGTFS")) {
                            switch (columnValue) {
                                case "电话":
                                    columnValue = "0";
                                    break;
                                case "QQ":
                                    columnValue = "1";
                                    break;
                                case "面对面":
                                    columnValue = "2";
                                    break;
                                default:
                                    columnValue = "3";
                                    break;
                            }
                        } else if (columnName.equals("ID_MARRIAGE")) {
                            //婚姻id
                            switch (columnValue) {
                                case "402881a85417c6ec015417dfc285000e":
                                    columnValue = "1";
                                    break;
                                case "402881a85417c6ec015417df521f000d":
                                    columnValue = "2";
                                    break;
                                default:
                                    break;
                            }
                        }
                        mysqlPreparedStatement.setString(parameterIndex, columnValue);
                        parameterIndex++;
                    }
                }

                mysqlPreparedStatement.executeUpdate();
                mysqlPreparedStatement.close();
            }
            log.info("表:{},导入成功", dto.getMysqlTable());
            oracleResultSet.close();
            oracleStatement.close();
        }

    }

    /**
     * 获取个别表需处理的数据
     *
     * @return
     */
    private List<IndividualDto> getIndividuallyData() {
        List<IndividualDto> list = new ArrayList<>();
        //导基础数据
        list.add(new IndividualDto("DICTPAYWAY", "md_dictpayway",
                Arrays.asList("ID", "PAYWAY_NAME", "INPUT_CODE", "PAYWAY_CODE", "F_RETURNTOCASH", "ID_RECEIPTTYPE_ORG", "ID_RECEIPTTYPE_PERSON", "NOTE", "F_HIDEONDAILYREPORT", "THING_KINGDEE_PAYWAYNAME", "THING_KINGDEE_USE_STATUS", "GROUP_KINGDEE_NUMBER", "GROUP_KINGDEE_PAYWAYNAME", "GROUP_KINGDEE_USE_STATUS", "POS_KINGDEE_NUMBER", "POS_KINGDEE_PAYWAYNAME", "POS_KINGDEE_USE_STATUS", "KINGDEE_COMPANY", "KINGDEE_SALEER", "THING_KINGDEE_NUMBER", "VACCINE", "PLSX", "IS_CHANGE", "IS_DELETE", "CREATEDATE", "MODIFYDATE"),
                Arrays.asList("id", "payway_name", "input_code", "payway_code", "f_returntocash", "id_receipttype_org", "id_receipttype_person", "note", "f_hideondailyreport", "thing_kingdee_paywayname", "thing_kingdee_use_status", "group_kingdee_number", "group_kingdee_paywayname", "group_kingdee_use_status", "pos_kingdee_number", "pos_kingdee_paywayname", "pos_kingdee_use_status", "kingdee_company", "kingdee_saleer", "thing_kingdee_number", "vaccine", "seq", "is_change", "is_delete", "createdate", "modifydate")
        ));
        list.add(new IndividualDto("PACS_BASEXAMLTEM", "md_pacs_basexamltem",
                Arrays.asList("ID", "EXAMITEM_NAME", "EXAMITEM_NAMEABR", "EXAMITEM_NAMEPRN", "EXAMITEM_NAMEENG", "EXAMITEM_NAMEENGABR", "F_MALE", "F_FEMALE", "INPUT_CODE", "DIVISION_ID", "TYPE", "CREATEDATE", "MODIFYDATE", "IS_DELETE"),
                Arrays.asList("id", "examitem_name", "examitem_nameabr", "examitem_nameprn", "examitem_nameeng", "examitem_nameengabr", "f_male", "f_female", "input_code", "division_id", "type", "createdate", "modifydate", "is_delete")
        ));
        list.add(new IndividualDto("PACS_ITEMS", "md_pacs_items",
                Arrays.asList("ID", "SYSMANMARK", "EXAMFEEITEM_NAME", "EXAMFEEITEM_NAMEABR", "EXAMFEEITEM_NAMEPRN", "EXAMFEEITEM_NAMEENG", "EXAMFEEITEM_NAMEENGABR", "EXAMFEEITEM_CODE", "EXAMFEEITEM_CODEHM", "X_XXDM", "ID_DEPART", "DEPART_NAME", "SFXMSRM", "XH", "JCYY", "XB", "CREATEDATE", "MODIFYDATE", "IS_DELETE"),
                Arrays.asList("id", "sysmanmark", "examfeeitem_name", "examfeeitem_nameabr", "examfeeitem_nameprn", "examfeeitem_nameeng", "examfeeitem_nameengabr", "examfeeitem_code", "examfeeitem_codehm", "x_xxdm", "id_depart", "depart_name", "sfxmsrm", "xh", "jcyy", "xb", "createdate", "modifydate", "is_delete")
        ));
        list.add(new IndividualDto("QX_USERS", "sys_user",
                Arrays.asList("ID", "DEPID", "USERNAME", "NAME", "EMAIL", "PHONE", "PASSWORD", "SDISCOUNT", "LDISCOUNT", "ISLEADER", "CID", "USERENABLED", "INPUT_CODE", "RECIVE_CODE", "IS_DOC", "USER_CODE", "PICTURE", "SIGN_PIC", "SUPERIOR_ID", "ISBJ", "SECRET_PASSWORD", "IS_DELETE", "LOGINIP", "LOGINDATE", "EXP_DATE", "LOCKDATE", "UNLOCKDATE", "KINGDEE_USE_STATUS", "KINGDEE_ACCOUNT_NO", "ENTRY_DATE"),
                Arrays.asList("user_no", "dept_id", "user_name", "nick_name", "email", "phonenumber", "password", "sdiscount", "ldiscount", "isleader", "cid", "status", "input_code", "recive_code", "is_doc", "user_code", "picture", "sign_pic", "superior_id", "isbj", "secret_password", "del_flag", "login_ip", "login_date", "exp_date", "lock_date", "unlock_date", "kingdee_use_status", "kingdee_account_no", "entry_date")
        ));

        //没用到的
//        list.add(new IndividualDto("CLIENTCOMMON", "crm_clientcommon",
//                Arrays.asList("ID", "KHDWMC", "KHDWSRM", "KHDWLXR", "KHDH", "SSHY", "XSJL", "DWJGDM", "KHDWDZ", "FZXID", "XSJLID", "LQCSTJ", "FPZT", "FRDWMC", "FDDBR", "YZBM", "QYGM", "QYJJLX", "ZYWSFZR", "KHDWZCDZ", "ZYWSGLJG", "ZCLX", "LSGX", "SJZGDW", "SJCYRS", "LDRS", "SCGRS", "KHSCTJDWDZ", "BZ", "IS_DELETE", "CREATEDATE", "MODIFYDATE"),
//                Arrays.asList("id", "khdwmc", "khdwsrm", "khdwlxr", "khdh", "sshy", "xsjl", "dwjgdm", "khdwdz", "fzxid", "xsjlid", "lqcstj", "fpzt", "frdwmc", "fddbr", "yzbm", "qygm", "qyjjlx", "zywsfzr", "khdwzcdz", "zywsgljg", "zclx", "lsgx", "sjzgdw", "sjcyrs", "ldrs", "scgrs", "khsctjdwdz", "bz", "is_delete", "createdate", "modifydate")
//        ));
//        list.add(new IndividualDto("PEIS_QUESTIONNAIRE_SECOND", "md_peis_questionnaire_second",
//                Arrays.asList("ID", "CREATEDATE", "MODIFYDATE", "PATIENTCODE", "PATIENTNAME", "ID_SEX", "BIRTHDATE", "IDCARDNO", "IS_HAN", "ID_PROVINCE", "ID_CITY", "ID_AREA", "ID_MARRIAGE", "PHONE", "FAMILY1", "FAMILY2", "FAMILY3", "FAMILY4", "PRESENT1", "PRESENT2", "PRESENT3", "ALLERGY1", "MEDICATION1", "MEDICATION2", "OPERATION1", "BODY1", "BODY2", "BODY3", "BODY4", "BODY5", "BODY6", "BODY7", "BODY8", "BODY9", "SMOKE1", "SMOKE2", "SMOKE3", "SMOKE4", "DRINK1", "SPORT1", "SPORT2", "SPORT3", "ENVIRONMENT1", "SLEEP1", "SLEEP2INT", "EXAMINATION1", "CREATOR", "SLEEP2_OTHER", "MEDICATION2_OTHER", "PRESENT2_OTHER", "FAMILY3_OTHER", "FAMILY2_OTHER", "OPERATION1_OTHER", "APP_USER_ID", "SLEEP2", "BODY10", "PATIENTBIZNO", "BMI", "HEIGHT", "WEIGHT", "OPERATION2", "OPERATION2_OTHER", "BIRTH1", "BIRTH2", "BIRTH2_OTHER", "BIRTH3", "BIRTH4", "BIRTH5", "BIRTH6", "BIRTH7", "BIRTH8", "BIRTH9", "BIRTH10", "BODY21", "BODY22", "BODY23", "BODY24", "BODY25", "BODY26", "BODY31", "BODY32", "BODY33", "BODY41", "BODY71", "BODY72", "BODY73", "BODY81", "BODY82", "BODY91", "BODY92", "EAT1", "EAT2", "EAT3", "EAT4", "EAT5", "EAT6", "EAT7", "EAT8", "EAT9", "EAT10", "EAT11", "EAT12", "EAT13", "EAT14", "EAT15", "EAT16", "EAT17", "EAT18", "EAT19", "DRINK2", "DRINK3", "DRINK4", "DRINK5", "SPORT11", "SPORT5", "SPORT6", "SPORT7", "SPORT8", "SPORT9", "SPIRIT1", "SPIRIT2", "SPIRIT3", "SPIRIT4", "SPIRIT5", "SPIRIT6", "SPIRIT7", "SPIRIT8", "SPIRIT9", "SLEEP3", "SLEEP4", "EXAMINATION2", "EXAMINATION3", "EXAMINATION4", "EXAMINATION5", "EXAMINATION6", "EXAMINATION7", "EXAMINATION8", "EXAMINATION9", "EXAMINATION10", "EXAMINATION11", "EXAMINATION12", "EXAMINATION13", "EXAMINATION14", "EXAMINATION15", "EXAMINATION16", "EXAMINATION17", "EXAMINATION18", "EXAMINATION19", "BIRTH8_OTHER", "PRESENT21", "PRESENT21_OTHER", "ALLERGY2"),
//                Arrays.asList("id", "createdate", "modifydate", "patientcode", "patientname", "id_sex", "birthdate", "idcardno", "is_han", "id_province", "id_city", "id_area", "id_marriage", "phone", "family1", "family2", "family3", "family4", "present1", "present2", "present3", "allergy1", "medication1", "medication2", "operation1", "body1", "body2", "body3", "body4", "body5", "body6", "body7", "body8", "body9", "smoke1", "smoke2", "smoke3", "smoke4", "drink1", "sport1", "sport2", "sport3", "environment1", "sleep1", "sleep2int", "examination1", "creator", "sleep2_other", "medication2_other", "present2_other", "family3_other", "family2_other", "operation1_other", "app_user_id", "sleep2", "body10", "patientbizno", "bmi", "height", "weight", "operation2", "operation2Other", "birth1", "birth2", "birth2Other", "birth3", "birth4", "birth5", "birth6", "birth7", "birth8", "birth9", "birth10", "body21", "body22", "body23", "body24", "body25", "body26", "body31", "body32", "body33", "body41", "body71", "body72", "body73", "body81", "body82", "body91", "body92", "eat1", "eat2", "eat3", "eat4", "eat5", "eat6", "eat7", "eat8", "eat9", "eat10", "eat11", "eat12", "eat13", "eat14", "eat15", "eat16", "eat17", "eat18", "eat19", "drink2", "drink3", "drink4", "drink5", "sport11", "sport5", "sport6", "sport7", "sport8", "sport9", "spirit1", "spirit2", "spirit3", "spirit4", "spirit5", "spirit6", "spirit7", "spirit8", "spirit9", "sleep3", "sleep4", "examination2", "examination3", "examination4", "examination5", "examination6", "examination7", "examination8", "examination9", "examination10", "examination11", "examination12", "examination13", "examination14", "examination15", "examination16", "examination17", "examination18", "examination19", "birth8Other", "present21", "present21Other", "allergy2")
//        ));
//
//
//        list.add(new IndividualDto("PEISPATIENT", "md_peispatient",
//                Arrays.asList("ID", "ID_CIS", "PATIENTARCHIVENO", "PATIENTCODE", "PATIENTBIZNO", "IDCARDNO", "NUMORGRESV", "PATIENTNAME", "INPUT_CODE", "ID_ORGRESERVATIONGROUP", "ID_ORGRESERVATION", "ID_ORG", "ORG_NAME", "ORG_DEPART", "WORKNO", "ID_FEETYPE", "ID_PAYWAY", "PAYWAY", "PERSONPRICELIMIT", "ID_SEX", "BIRTHDATE", "AGE", "ID_MARRIAGE", "MARRIAGE", "ID_NATION", "NATION", "ADDRESS", "ID_INFORMWAY", "ID_OPENDOCTOR", "EMAIL", "PHONE", "ID_PATIENTCLASS", "ID_RESAREA", "RESAREA", "F_ISFORPREPARE", "F_ISFORRESERVE", "F_REGISTERED", "DATEREGISTER", "MONEYAMOUNT", "MONEYAMOUNTPAID", "GUIDANCENOTE", "ID_DOCTORREG", "DOCTORREG", "ID_EXAMTYPE", "EXAMSUITE_NAME", "EXAMSUITE_ALIAS", "ID_DOCTORAPPLY", "DOCTORAPPLY", "F_GUIDANCEPRINTED", "F_EXAMSTARTED", "F_READYTOFINAL", "F_PAUSED", "F_FINALLOCKED", "F_FINALEXAMED", "ID_DOCTORFINAL", "DOCTORFINAL_NAME_R", "DATEFINALEXAMED", "F_CLOSED", "DATECLOSED", "NOTE", "KNOWLEDGE", "TIMINGSTARTEDAT", "HOSPITALCODE", "ID_EXAMPLACE", "PARSEDASSIGNEDSUITEANDFI", "PARSEDASSIGNEDGROUPANDFI", "PARSEDSUITEANDFI", "PARSEDSUITEANDFILAB", "ID_GUIDENURSE", "PATIENTNAMEENCODED", "PATIENTCODEHIDEN", "F_WORDPRINTED", "GUIDANCENOTE2", "F_USECODEHIDEN", "ID_PATIENTCLASS3", "DATEREGISTERNOTIME", "COUNTERREPORTPRINTED", "F_ISRECHECK", "F_SETTLENONE", "DATEGUIDANCERETURNED", "COUNTREPORTOCCUPATION", "PATIENTNAMERECEIPT", "INSTANCETAG", "F_OUTPATIENT", "STATUSOFHM", "PATIENTNAMEPINYIN", "INPATIENTNO", "INSURANCENO", "COUNTREPORTXML", "COUNTREPORTCOMPARE", "COUNTREPORTOCCUPATIONPDF", "COUNTREPORTOCCUPATIONXML", "ID_TJTC", "JZDW", "JZDWR", "SPR", "TJR", "LQFS", "YZBM", "YJADDRESS", "QTXZ", "IS_HMDB", "IS_HMD", "ISJJ", "ZGL", "JHGL", "JHYS", "JKTJZT", "ZYTJZT", "TMYD", "MEDICALDATE", "TRADES", "CJJGSFYHF", "BHGYBSFYHF", "YXJGSFYHF", "MEDICALTYPE", "PREPAYMENT", "TCPRICE", "CULTURAL", "WORK_DATE", "HARM_DATE", "DISEASE_PRINT_NUM", "HEALTH_PRINT_NUM", "READYTOFINAL_DATE", "GUIDE_SIGNLE_COUNT", "SHORT_CODE", "IS_NOTICED", "REVIEW_PDF", "CONTRAINDICATED_PDF", "DISEASE_PDF", "TS_LIMIT", "CHECKOUT_DATE", "CHECKOUT_STATUS", "WORKTYPE_ID", "ID_EXAMCLASS", "ORIGINAL_TRADE", "CREATEDATE", "MODIFYDATE"),
//                Arrays.asList("id", "id_cis", "patientarchiveno", "patientcode", "patientbizno", "idcardno", "numorgresv", "patientname", "input_code", "id_orgreservationgroup", "id_orgreservation", "id_org", "org_name", "org_depart", "workno", "have_private", "id_payway", "payway", "personpricelimit", "id_sex", "birthdate", "age", "id_marriage", "marriage", "id_nation", "nation", "address", "id_informway", "id_opendoctor", "email", "phone", "id_patientclass", "id_resarea", "resarea", "f_isforprepare", "f_isforreserve", "f_registered", "dateregister", "moneyamount", "moneyamountpaid", "guidancenote", "id_doctorreg", "doctorreg", "id_examtype", "examsuite_name", "examsuite_alias", "id_doctorapply", "doctorapply", "f_guidanceprinted", "f_examstarted", "f_readytofinal", "f_paused", "f_finallocked", "f_finalexamed", "id_doctorfinal", "doctorfinal_name_r", "datefinalexamed", "f_closed", "dateclosed", "note", "knowledge", "timingstartedat", "hospitalcode", "id_examplace", "parsedassignedsuiteandfi", "parsedassignedgroupandfi", "parsedsuiteandfi", "parsedsuiteandfilab", "id_guidenurse", "patientnameencoded", "patientcodehiden", "f_wordprinted", "guidancenote2", "f_usecodehiden", "id_patientclass3", "dateregisternotime", "counterreportprinted", "f_isrecheck", "f_settlenone", "dateguidancereturned", "countreportoccupation", "patientnamereceipt", "instancetag", "f_outpatient", "statusofhm", "patientnamepinyin", "inpatientno", "insuranceno", "countreportxml", "countreportcompare", "countreportoccupationpdf", "countreportoccupationxml", "id_tjtc", "jzdw", "jzdwr", "spr", "tjr", "lqfs", "yzbm", "yjaddress", "qtxz", "is_hmdb", "is_hmd", "isjj", "zgl", "jhgl", "jhys", "jktjzt", "zytjzt", "tmyd", "medicaldate", "trades", "cjjgsfyhf", "bhgybsfyhf", "yxjgsfyhf", "medicaltype", "prepayment", "tcprice", "cultural", "work_date", "harm_date", "disease_print_num", "health_print_num", "readytofinal_date", "guide_signle_count", "short_code", "is_noticed", "review_pdf", "contraindicated_pdf", "disease_pdf", "ts_limit", "checkout_date", "checkout_status", "worktype_id", "id_examclass", "original_trade", "createdate", "modifydate")
//        ));
//        list.add(new IndividualDto("CUSTOMERCOMMUNICATE", "crm_customercommunicate",
//                Arrays.asList("ID", "KHDWMC", "KHLXDH", "SCTJKSRQ", "GTJG", "GTRQ", "XCGTRQ", "BCGTFS", "XSJL", "BZ", "XSJLID", "FZXID", "CREATEDATE", "MODIFYDATE"),
//                Arrays.asList("id", "khdwmc", "khlxdh", "sctjksrq", "gtjg", "gtrq", "xcgtrq", "bcgtfs", "xsjl", "bz", "xsjlid", "fzxid", "createdate", "modifydate")
//        ));

        return list;
    }


    /**
     * 获取数据库的对应关系,0是oracle,1是mysql
     *
     * @return
     */
    private List<String[]> findTableNameList() {
        List<String[]> list = new ArrayList<>();
        //0是oracle,1是mysql
        //导入基础数据等表
        list.add(new String[]{"BASE_DICTIONARY", "base_dictionary"});
        list.add(new String[]{"BASE_DICTIONARY_CLASS", "base_dictionary_class"});
        list.add(new String[]{"BASE_DICTIONARY_MATCH", "base_dictionary_match"});
        list.add(new String[]{"SELLPRINTTEAMS", "crm_sellprintteams"});
        list.add(new String[]{"KINGDEECUSTOMER", "kd_customer"});
        list.add(new String[]{"KINGDEEDEPARTMENT", "kd_department"});
        list.add(new String[]{"KINGDEEORGANIZATION", "kd_organization"});
        list.add(new String[]{"KINGDEEPAYWAY", "kd_payway"});
        list.add(new String[]{"KINGDEERESER", "kd_reser"});
        list.add(new String[]{"KINGDEERESERWAY", "kd_reserway"});
        list.add(new String[]{"SALEER", "kd_saleer"});
        list.add(new String[]{"AREA", "md_area"});
        list.add(new String[]{"BAS_MERGE", "md_bas_merge"});
        list.add(new String[]{"ATTACHMENT_CONFIG", "md_attachment_config"});
        list.add(new String[]{"BAS_MERGE_CONCLUSION", "md_bas_merge_conclusion"});
        list.add(new String[]{"BASCONCLUSION", "md_basconclusion"});
        list.add(new String[]{"BASCONCLUSIONTYPE", "md_basconclusiontype"});
        list.add(new String[]{"BASE_ADICON_EXAM_ITEM_CODE", "md_base_adicon_exam_item_code"});
        list.add(new String[]{"BASE_ATTACHMENT_CONFIG", "md_base_attachment_config"});
        list.add(new String[]{"BASE_GUIDE_MEAL", "md_base_guide_meal"});
        list.add(new String[]{"BASE_GUIDE_MEALITEM", "md_base_guide_mealitem"});
        list.add(new String[]{"BASE_INDUSTRY", "md_base_industry"});
        list.add(new String[]{"BASE_UNIT", "md_base_unit"});
        list.add(new String[]{"BASE_WORKTYPE", "md_base_worktype"});
        list.add(new String[]{"BASE_ZONE", "md_base_zone"});
        list.add(new String[]{"BASE_ZONE_QD", "md_base_zone_qd"});
        list.add(new String[]{"BASEXAMLTEM", "md_basexamltem"});
        list.add(new String[]{"BASEXAMLTEM_SIGN", "md_basexamltem_sign"});
        list.add(new String[]{"BASEXAMLTEMTYPE", "md_basexamltemtype"});
        list.add(new String[]{"CARD_TYPE", "md_card_type"});
        list.add(new String[]{"CARMANAGE", "md_carmanage"});
        list.add(new String[]{"COMBOANDFZX", "md_comboandfzx"});
        list.add(new String[]{"COMBOANDITEM", "md_comboanditem"});
        list.add(new String[]{"COMBOEXAMITEM", "md_comboexamitem"});
        list.add(new String[]{"CONCLUSION_AND_FZX", "md_conclusion_and_fzx"});
        list.add(new String[]{"CREATECOMBO", "md_createcombo"});
        list.add(new String[]{"CREATECOMBO_TYPE", "md_createcombo_type"});
        list.add(new String[]{"DEVICETYPE_POSITION_CHECKITEM", "md_devicetype_position_checkitem"});
        list.add(new String[]{"DRINKING_TYPE", "md_drinking_type"});
        list.add(new String[]{"DRUG_AND_FZX", "md_drug_and_fzx"});
        list.add(new String[]{"DRUG_DISEASE", "md_drug_disease"});
        list.add(new String[]{"DRUG_DISEASE_TYPE", "md_drug_disease_type"});
        list.add(new String[]{"EMPHASIS_ASK_SYMPTOM", "md_emphasis_ask_symptom"});
        list.add(new String[]{"EXAM_AND_FZX", "md_exam_and_fzx"});
        list.add(new String[]{"EXPRESSCOMPANY", "md_expresscompany"});
        list.add(new String[]{"FYLX", "md_fylx"});
        list.add(new String[]{"HARM", "md_harm"});
        list.add(new String[]{"HARM_AND_FZX", "md_harm_and_fzx"});
        list.add(new String[]{"HARM_PACKAGE_MATCH", "md_harm_package_match"});
        list.add(new String[]{"INSPECT_CHARGE", "md_inspect_charge"});
        list.add(new String[]{"INTERFACE_ACCOUNT", "md_interface_account"});
        list.add(new String[]{"ITEMS", "md_items"});
        list.add(new String[]{"ITEMS_AND_FZX", "md_items_and_fzx"});
        list.add(new String[]{"ITEMS_BARCODE_CLASS", "md_items_barcode_class"});
        list.add(new String[]{"NATION", "md_nation"});
        list.add(new String[]{"NOTIFICATIONMETHOD", "md_notificationmethod"});
        list.add(new String[]{"OCCUPATION_DISEAST", "md_occupation_diseast"});
        list.add(new String[]{"OCCUPATION_DISEAST_CLASS", "md_occupation_diseast_class"});
        list.add(new String[]{"OCCUPATION_DRUG", "md_occupation_drug"});
        list.add(new String[]{"OCCUPATION_SYMPTOM", "md_occupation_symptom"});
        list.add(new String[]{"PACS_BASCONCLUSION", "md_pacs_basconclusion"});
        list.add(new String[]{"PACS_BASE_PART", "md_pacs_base_part"});
        list.add(new String[]{"PACS_BASEXAMLTEM_SIGN", "md_pacs_basexamltem_sign"});
        list.add(new String[]{"PACS_INSPECT_CHARGE", "md_pacs_inspect_charge"});
        list.add(new String[]{"PACS_ITEM_PART", "md_pacs_item_part"});
        list.add(new String[]{"PACS_PDF", "md_pacs_pdf"});
        list.add(new String[]{"PRINTTYPE", "md_printtype"});
        list.add(new String[]{"QYJJLX", "md_qyjjlx"});
        list.add(new String[]{"RECEIPT_TYPE", "md_receipt_type"});
        list.add(new String[]{"REVIEW_NOTIFICATION_FORMAT", "md_review_notification_format"});
        list.add(new String[]{"SHORT_MESSAGE_TYPE", "md_short_message_type"});
        list.add(new String[]{"SHORTMESSAGE", "md_shortmessage"});
        list.add(new String[]{"SSHY", "md_sshy"});
        list.add(new String[]{"STENCIL_MAINTAIN", "md_stencil_maintain"});
        list.add(new String[]{"UPPEROWER", "md_upperower"});
        list.add(new String[]{"USER_HARM_CLASS", "md_user_harm_class"});
        list.add(new String[]{"WHYSQZFW", "md_whysqzfw"});
        list.add(new String[]{"WSJG", "md_wsjg"});
        list.add(new String[]{"WZ_JWB", "md_wz_jwb"});
        list.add(new String[]{"WZ_JZB", "md_wz_jzb"});
        list.add(new String[]{"YBLX", "md_yblx"});
        list.add(new String[]{"YEAR", "md_year"});
        list.add(new String[]{"ZY_FHCL_GR", "md_zy_fhcl_gr"});
        list.add(new String[]{"ZY_FHCS_GC", "md_zy_fhcs_gc"});
        list.add(new String[]{"ZY_FHCS_GC_CLASS", "md_zy_fhcs_gc_class"});
        list.add(new String[]{"ZY_FHCS_GR_CLASS", "md_zy_fhcs_gr_class"});
        list.add(new String[]{"ZY_HARM_CLASS", "md_zy_harm_class"});
        list.add(new String[]{"ZY_OCCUPATION_ORG", "md_zy_occupation_org"});
        list.add(new String[]{"ZY_SUMMARY", "md_zy_summary"});
        list.add(new String[]{"ZY_VS_SUMMARY", "md_zy_vs_summary"});
        list.add(new String[]{"PATIENTTYPE", "md_patienttype"});


        //不存在
//        list.add(new String[]{"DRUGSTORE_CLASS", "md_drugstore_class"});
//        list.add(new String[]{"DRUGSTORE_DRUG", "md_drugstore_drug"});
//
//
//        //不需要导入的表
//        list.add(new String[]{"KINGDEEREMITTANCE", "kd_remittance"});
//        list.add(new String[]{"QX_USE_DEP", "sys_user_dep"});
//        list.add(new String[]{"PEIS_QUESTIONNAIRE", "md_peis_questionnaire"});
//        list.add(new String[]{"PUFFTUBE", "md_pufftube"});
//        list.add(new String[]{"CARD", "md_card"});
////        list.add(new String[]{"CARD_MEMBER_MEDICAL", "md_card_member_medical"});
//        list.add(new String[]{"CARD_PAYWAY", "md_card_payway"});
//        list.add(new String[]{"CHEST", "md_chest"});
//        list.add(new String[]{"GROUP_AND_FZX", "md_group_and_fzx"});
//
//        list.add(new String[]{"BATCH_SMS_RECORD", "md_batch_sms_record"});
//        list.add(new String[]{"CARD_RECHECK_RECORD", "md_card_recheck_record"});
//        list.add(new String[]{"CONSULATION", "md_consulation"});
//        list.add(new String[]{"DINING_RECORD", "md_dining_record"});
//        list.add(new String[]{"DRUGSTORE_PRESCRIBE", "md_drugstore_prescribe"});
//        list.add(new String[]{"BALL_CHECK_REPORT", "md_ball_check_report"});
//        list.add(new String[]{"BK_PATIENT", "md_bk_patient"});
//        list.add(new String[]{"BK_PATIENTFEEITEM", "md_bk_patientfeeitem"});
//        list.add(new String[]{"COMMENTS_PROGESSIONAL", "md_comments_progessional"});
//        list.add(new String[]{"COMPARE_REPORT", "md_compare_report"});
//        list.add(new String[]{"CREATEMEAL", "md_createmeal"});
//        list.add(new String[]{"CREATEORDER", "md_createorder"});
//        list.add(new String[]{"CREATE_ORDER_QTXZ", "md_create_order_qtxz"});
//        list.add(new String[]{"CUSTOMERFOLLOW", "crm_customerfollow"});
//        list.add(new String[]{"CUSTOMER_OPERATE_HISTORY", "crm_customer_operate_history"});
//        list.add(new String[]{"CUSTOMER_TRANSFER", "crm_customer_transfer"});
//        list.add(new String[]{"CUST_FEEDBACK", "md_cust_feedback"});
//        list.add(new String[]{"DANAGER_OBJECT", "md_danager_object"});
//        list.add(new String[]{"DW_HARM", "md_dw_harm"});
//        list.add(new String[]{"FAIL_TOTAL_VISIT", "md_fail_total_visit"});
//        list.add(new String[]{"FAMILY_CARD_CHARGE", "md_family_card_charge"});
//        list.add(new String[]{"FILE_CHANGE_RECORD", "md_file_change_record"});
//        list.add(new String[]{"FINANCEINPUT", "md_financeinput"});
//        list.add(new String[]{"FX_COMPLETION", "md_fx_completion"});
//        list.add(new String[]{"FX_DETECTION", "md_fx_detection"});
//        list.add(new String[]{"FX_DETECTIONZY", "md_fx_detectionzy"});
//        list.add(new String[]{"FX_HARM", "md_fx_harm"});
//        list.add(new String[]{"FX_ITEMSCHECK", "md_fx_itemscheck"});
//        list.add(new String[]{"FX_NEGATIVE", "md_fx_negative"});
//        list.add(new String[]{"FX_PERSONNELVIEW", "md_fx_personnelview"});
//        list.add(new String[]{"FX_POSITIVE", "md_fx_positive"});
//        list.add(new String[]{"FX_REVIEWSITUATION", "md_fx_reviewsituation"});
//        list.add(new String[]{"FX_REVIEW_INFO", "md_fx_review_info"});
//        list.add(new String[]{"FX_SUMMARY", "md_fx_summary"});
//        list.add(new String[]{"HANDLE_NEW_PROJECTS", "md_handle_new_projects"});
//        list.add(new String[]{"INSPECT_REPORT", "md_inspect_report"});
//        list.add(new String[]{"LABORATORY_RESULT", "md_laboratory_result"});
//        list.add(new String[]{"LEADERTARGET", "md_leadertarget"});
//        list.add(new String[]{"LIMIT_OPERATION", "md_limit_operation"});
//        list.add(new String[]{"LUNG", "md_lung"});
//        list.add(new String[]{"MEALANDFZX", "md_mealandfzx"});
//        list.add(new String[]{"MEALANDITEM", "md_mealanditem"});
//        list.add(new String[]{"MEMBERBIRTHDAT", "md_memberbirthdat"});
//        list.add(new String[]{"MEMBERINTEGRAL", "md_memberintegral"});
//        list.add(new String[]{"NOTIFIER", "md_notifier"});
//        list.add(new String[]{"NOTIFY_SMS_EXAM", "md_notify_sms_exam"});
//        list.add(new String[]{"NOTIFY_SMS_VISIT", "md_notify_sms_visit"});
//        list.add(new String[]{"NUCLEIN", "md_nuclein"});
//        list.add(new String[]{"OPERATE_LOG", "md_operate_log"});
//        list.add(new String[]{"ORDERANDCOMBO", "md_orderandcombo"});
//        list.add(new String[]{"ORDERANDFZX", "md_orderandfzx"});
//        list.add(new String[]{"ORDERSUMMARY", "md_ordersummary"});
//        list.add(new String[]{"OTHER_REPORT", "md_other_report"});
//        list.add(new String[]{"OUTSIDE_CHARGE_ITEM", "md_outside_charge_item"});
//        list.add(new String[]{"OUTSIDE_CHECKIN", "md_outside_checkin"});
//        list.add(new String[]{"OUTSIDE_HAND", "md_outside_hand"});
//        list.add(new String[]{"OUTSIDE_MAIN", "md_outside_main"});
//        list.add(new String[]{"OUTSIDE_PICTRUE", "md_outside_pictrue"});
//        list.add(new String[]{"PA_PEISSORTEXAM", "md_pa_peissortexam"});
//        list.add(new String[]{"PEISORGRESERVATION", "md_peisorgreservation"});
//        list.add(new String[]{"PEISORGRESERVATIONGROUP", "md_peisorgreservationgroup"});
//        list.add(new String[]{"PEISORGRESERVATIONRECEIPT", "md_peisorgreservationreceipt"});
//        list.add(new String[]{"PEISPATIENTARCHIVE", "md_peispatientarchive"});
//        list.add(new String[]{"PEISPATIENT_AND_FZX", "md_peispatient_and_fzx"});
//        list.add(new String[]{"PEISPATIENT_CHARGE_MAIN", "md_peispatient_charge_main"});
//        list.add(new String[]{"PEISPATIENT_CHARGE_OTHER", "md_peispatient_charge_other"});
//        list.add(new String[]{"PEISPATIENT_CHARGE_RECORD", "md_peispatient_charge_record"});
//        list.add(new String[]{"PEISPATIENT_CONSULTATION", "md_peispatient_consultation"});
//        list.add(new String[]{"PEISPATIENT_CONSULTATION_PIC", "md_peispatient_consultation_pic"});
//        list.add(new String[]{"PEISPATIENT_PHOTO", "md_peispatient_photo"});
//        list.add(new String[]{"PEISPATIENT_RESERVATION_CHARGE", "md_peispatient_reservation_charge"});
//        list.add(new String[]{"PEISSORTEXAM", "md_peissortexam"});
//        list.add(new String[]{"PEIS_OL", "md_peis_ol"});
//        list.add(new String[]{"PEIS_RESER_PAYWAY", "md_peis_reser_payway"});
//        list.add(new String[]{"PEIS_STATE", "md_peis_state"});
//        list.add(new String[]{"PRICTURE", "md_pricture"});
//        list.add(new String[]{"QX_WS_BK_DICTIONARY", "ws_bk_dictionary"});
//        list.add(new String[]{"QX_WS_DEPARTMENT", "ws_department"});
//        list.add(new String[]{"QX_WS_LOG", "ws_log"});
//        list.add(new String[]{"QX_WS_RESOURCE", "ws_resource"});
//        list.add(new String[]{"QX_WS_ROLES", "ws_roles"});
//        list.add(new String[]{"QX_WS_ROLE_RESOURCE", "ws_role_resource"});
//        list.add(new String[]{"QX_WS_USERS", "ws_users"});
//        list.add(new String[]{"QX_WS_USER_ROLE", "ws_user_role"});
//        list.add(new String[]{"RECEIVEANDSELL", "crm_receiveandsell"});
//        list.add(new String[]{"RESERVATION_RETURN_VISIT", "md_reservation_return_visit"});
//        list.add(new String[]{"REVIEW", "md_review"});
//        list.add(new String[]{"REVIEW_PROJECT", "md_review_project"});
//        list.add(new String[]{"RISKCLIENT", "md_riskclient"});
//        list.add(new String[]{"RISKCLIENTCON", "md_riskclientcon"});
//        list.add(new String[]{"SAMPLE_CONNECT", "md_sample_connect"});
//        list.add(new String[]{"SAMPLE_DELIVERY", "md_sample_delivery"});
//        list.add(new String[]{"SAMPLE_PERSON", "md_sample_person"});
//        list.add(new String[]{"SATISFACTION", "md_satisfaction"});
//        list.add(new String[]{"SAVEFILEPATH", "md_savefilepath"});
//        list.add(new String[]{"SECTION_AND_REMIND", "md_section_and_remind"});
//        list.add(new String[]{"SECTION_REMIND", "md_section_remind"});
//        list.add(new String[]{"SECTION_RESULT_PLAN", "md_section_result_plan"});
//        list.add(new String[]{"SECTION_TOTAL", "md_section_total"});
//        list.add(new String[]{"SELLCUSTOMER", "crm_sellcustomer"});
//        list.add(new String[]{"SELLPACT", "crm_sellpact"});
//        list.add(new String[]{"SELLTARGET", "crm_selltarget"});
//        list.add(new String[]{"SELL_OUTSIDE", "crm_sell_outside"});
//        list.add(new String[]{"SELL_REMIND", "crm_sell_remind"});
//        list.add(new String[]{"SH_REPORT", "md_sh_report"});
//        list.add(new String[]{"SMS_RECORD", "md_sms_record"});
//        list.add(new String[]{"SORTEXAM_LIMIT", "md_sortexam_limit"});
//        list.add(new String[]{"TB_PATIENT", "md_tb_patient"});
//        list.add(new String[]{"TEAMREMIND", "crm_teamremind"});
//        list.add(new String[]{"TEMPORARYQUEUE", "md_temporaryqueue"});
//        list.add(new String[]{"TEMP_FEEITEM", "md_temp_feeitem"});
//        list.add(new String[]{"TJBB_BMI", "md_tjbb_bmi"});
//        list.add(new String[]{"TJBB_GMD", "md_tjbb_gmd"});
//        list.add(new String[]{"TJBB_XTJC", "md_tjbb_xtjc"});
//        list.add(new String[]{"TJBB_XYJC", "md_tjbb_xyjc"});
//        list.add(new String[]{"TJBB_XZJC", "md_tjbb_xzjc"});
//        list.add(new String[]{"TJDW_BRANCH", "md_tjdw_branch"});
//        list.add(new String[]{"TOTAL_DOCTOR", "md_total_doctor"});
//
//        list.add(new String[]{"TRADE_RECORD", "md_trade_record"});
//        list.add(new String[]{"USERAUTHCODE", "md_userauthcode"});
//        list.add(new String[]{"USER_SALEER", "md_user_saleer"});
//        list.add(new String[]{"VATION_AND_FZX", "md_vation_and_fzx"});
//        list.add(new String[]{"VISIT_MAIN", "md_visit_main"});
//        list.add(new String[]{"VISIT_WRITE", "md_visit_write"});
//        list.add(new String[]{"PEISPATIENTHISTORY", "md_peispatienthistory"});
//        list.add(new String[]{"GROUP_BALANCE", "md_group_balance"});
//        list.add(new String[]{"MONTHTARGET", "md_monthtarget"});
//        list.add(new String[]{"SALES_TARGET_STATISTIC", "md_sales_target_statistic"});
//
//        list.add(new String[]{"GROUP_REVIEW_NOTICE", "md_group_review_notice"});
//        list.add(new String[]{"GROUP_REVIEW_NOTICE_PATIENT", "md_group_review_notice_patient"});
//        list.add(new String[]{"ADVANCE_VISIT", "md_advance_visit"});
//        list.add(new String[]{"ADVANCE_VISIT_WRITE", "md_advance_visit_write"});
//        list.add(new String[]{"WZ_CALLBACK", "md_wz_callback"});
//        list.add(new String[]{"WZ_GRXX", "md_wz_grxx"});
//        list.add(new String[]{"WZ_LATEST_RUMMAGER", "md_wz_latest_rummager"});
//        list.add(new String[]{"WZ_LIFE", "md_wz_life"});
//        list.add(new String[]{"WZ_SYMPTOM", "md_wz_symptom"});
//        list.add(new String[]{"WZ_TJDWXX", "md_wz_tjdwxx"});
//        list.add(new String[]{"WZ_TJDWXX_MX", "md_wz_tjdwxx_mx"});
//        list.add(new String[]{"WZ_TJRECORD", "md_wz_tjrecord"});
//        list.add(new String[]{"WZ_ZYBS", "md_wz_zybs"});
//        list.add(new String[]{"WZ_ZYS", "md_wz_zys"});
//        list.add(new String[]{"WZ_ZYS_WHYS", "md_wz_zys_whys"});
//        list.add(new String[]{"DESCRIBE", "md_describe"});
//        list.add(new String[]{"ELECTRO_AUDIOMETER", "md_electro_audiometer"});
//        list.add(new String[]{"PACS_PEISPATIENT", "md_pacs_peispatient"});
//        list.add(new String[]{"PACS_PEISPATIENTFEEITEM", "md_pacs_peispatientfeeitem"});
//        list.add(new String[]{"PACS_RESULT", "md_pacs_result"});
//        list.add(new String[]{"PACS_SECTION_RESULT_MAIN", "md_pacs_section_result_main"});
//        list.add(new String[]{"PACS_SECTION_RESULT_TWO", "md_pacs_section_result_two"});
//        list.add(new String[]{"PEISPATIENTEXAMITEM", "md_peispatientexamitem"});
//        list.add(new String[]{"PEISPATIENTFEEITEM", "md_peispatientfeeitem"});
//        list.add(new String[]{"REPORT", "md_report"});
//        list.add(new String[]{"REPORT_URL", "md_report_url"});
//        list.add(new String[]{"SECTION_RESULT_MAIN", "md_section_result_main"});
//        list.add(new String[]{"SECTION_RESULT_TWO", "md_section_result_two"});
//        list.add(new String[]{"TJREG", "md_tjreg"});
//        list.add(new String[]{"PEISPATIENTCHARGE", "md_peispatientcharge"});

        return list;
    }


//    /**
//     * 根据订单号导入老数据到新系统中
//     *
//     * @param ddhList
//     * @return
//     */
//    @Override
//    public Boolean importData(List<String> ddhList) {
//        log.info("开始执行导入操作,参数：{}", ddhList);
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        List<Future<Boolean>> futures = new ArrayList<>();
//
//        for (String ddh : ddhList) {
//            Future<Boolean> future = executorService.submit(() -> {
//                // 订单 createorder
//                Createorder createorder = orCreateorderService.getInfoByDdh(ddh);
//                if (ObjectUtil.isEmpty(createorder)) {
//                    log.info("未找到订单号{}的订单", ddh);
//                    return false;
//                }
//                mdCreateorderService.saOrUp(mapperFacade.map(createorder, MdCreateorder.class));
//                // 客户 SellCustomer
//                Sellcustomer sellcustomer = orSellcustomerService.getInfoById(createorder.getKhdwmcid());
//                crmSellcustomerService.saOrUp(mapperFacade.map(sellcustomer, CrmSellcustomer.class));
//                // 订单分中心关联表 OrderAndFzx
//                List<Orderandfzx> orderandfzxList = orOrderandfzxService.getByDdid(createorder.getId());
//                mdOrderandfzxService.saOrUpList(mapperFacade.mapAsList(orderandfzxList, MdOrderandfzx.class));
//                // 合同 SellPact
//                if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
//                    Sellpact sellpact = orSellpactService.getByHtbh(createorder.getHtbh());
//                    crmSellpactService.saOrUp(mapperFacade.map(sellpact, CrmSellpact.class));
//                }
//                // 发放方式 NotificationMethod 已同步
//                // 用户（开单销售、开单助理） QxUsers 直接改userNo就行吧
//                // 订单套餐关联表 OrderAndCombo
//                List<Orderandcombo> orderandcomboList = orOrderandcomboService.getByDdId(createorder.getId());
//                mdOrderandcomboService.saOrUpList(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));
//                for (Orderandcombo oac : orderandcomboList) {
//                    if ("0".equals(oac.getCombostate())) {
//                        // 套餐 CreateMeal
//                        Createmeal createmeal = orCreatemealService.getInfoById(oac.getTcid());
//                        mdCreatemealService.saOrUp(mapperFacade.map(createmeal, MdCreatemeal.class));
//                        // 普通套餐与收费项目 MealAndItem
//                        List<Mealanditem> mealanditemList = orMealanditemService.getByTcid(oac.getTcid());
//                        mdMealanditemService.saOrUpList(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
//                        // 普通套餐与分中心关联表 MealAndFzx
//                        List<Mealandfzx> mealandfzxList = orMealandfzxService.getByTcid(oac.getTcid());
//                        mdMealandfzxService.saOrUpList(mapperFacade.mapAsList(mealandfzxList, MdMealandfzx.class));
//                    } else {
//                        // 最小套餐 CreateCombo
//                        Createcombo createcombo = orCreatecomboService.getInfoById(oac.getTcid());
//                        mdCreatecomboService.saOrUp(mapperFacade.map(createcombo, MdCreatecombo.class));
//                        // 最小套餐与分中心关联表 ComboAndItem
//                        List<Comboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
//                        mdComboanditemService.saOrUpList(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
//                        // 最小套餐与收费项目 ComboAndFzx
//                        List<Comboandfzx> comboandfzxList = orComboandfzxService.getByTcid(oac.getTcid());
//                        mdComboandfzxService.saOrUpList(mapperFacade.mapAsList(comboandfzxList, MdComboandfzx.class));
//                    }
//                }
//                // 收费项目 Items更新过了
//                log.info("订单号:{}导入成功", ddh);
//                return true;
//            });
//            futures.add(future);
//        }
//        for (Future<Boolean> future : futures) {
//            try {
//                future.get();
//            } catch (Exception e) {
//                log.error("导入操作出现异常", e);
//            }
//        }
//
//        executorService.shutdown();
//        return true;
//    }
//
//
//    /**
//     * 导入未完成的体检者
//     *
//     * @return
//     */
//    @Override
//    public Boolean importPeispatient() {
//        //查询未结束的体检任务
//        List<Peisorgreservation> peisorgreservations = orPeisorgreservationService.getUnFinished();
//        mdPeisorgreservationService.saOrUpList(mapperFacade.mapAsList(peisorgreservations, MdPeisorgreservation.class));
//        //获取未完成的订单号
//        List<String> ddhList = peisorgreservations.stream()
//                .map(Peisorgreservation::getDdh)
//                .collect(Collectors.toList());
//        log.info("开始执行导入操作,参数：{}", ddhList);
//        for (String ddh : ddhList) {
//            // 订单 createorder
//            Createorder createorder = orCreateorderService.getInfoById(ddh);
//            if (ObjectUtil.isEmpty(createorder)) {
//                log.info("未找到订单号{}的订单", ddh);
//            } else {
//                mdCreateorderService.saOrUp(mapperFacade.map(createorder, MdCreateorder.class));
//                // 客户 SellCustomer
//                Sellcustomer sellcustomer = orSellcustomerService.getInfoById(createorder.getKhdwmcid());
//                crmSellcustomerService.saOrUp(mapperFacade.map(sellcustomer, CrmSellcustomer.class));
//                // 订单分中心关联表 OrderAndFzx
//                List<Orderandfzx> orderandfzxList = orOrderandfzxService.getByDdid(createorder.getId());
//                mdOrderandfzxService.saOrUpList(mapperFacade.mapAsList(orderandfzxList, MdOrderandfzx.class));
//                // 合同 SellPact
//                if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
//                    Sellpact sellpact = orSellpactService.getByHtbh(createorder.getHtbh());
//                    crmSellpactService.saOrUp(mapperFacade.map(sellpact, CrmSellpact.class));
//                }
//                // 发放方式 NotificationMethod 已同步
//                // 用户（开单销售、开单助理） QxUsers 直接改userNo就行吧
//                // 订单套餐关联表 OrderAndCombo
//                List<Orderandcombo> orderandcomboList = orOrderandcomboService.getByDdId(createorder.getId());
//                mdOrderandcomboService.saOrUpList(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));
//                for (Orderandcombo oac : orderandcomboList) {
//                    if ("0".equals(oac.getCombostate())) {
//                        // 套餐 CreateMeal
//                        Createmeal createmeal = orCreatemealService.getInfoById(oac.getTcid());
//                        mdCreatemealService.saOrUp(mapperFacade.map(createmeal, MdCreatemeal.class));
//                        // 普通套餐与收费项目 MealAndItem
//                        List<Mealanditem> mealanditemList = orMealanditemService.getByTcid(oac.getTcid());
//                        mdMealanditemService.saOrUpList(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
//                        // 普通套餐与分中心关联表 MealAndFzx
//                        List<Mealandfzx> mealandfzxList = orMealandfzxService.getByTcid(oac.getTcid());
//                        mdMealandfzxService.saOrUpList(mapperFacade.mapAsList(mealandfzxList, MdMealandfzx.class));
//                    } else {
//                        // 最小套餐 CreateCombo
//                        Createcombo createcombo = orCreatecomboService.getInfoById(oac.getTcid());
//                        mdCreatecomboService.saOrUp(mapperFacade.map(createcombo, MdCreatecombo.class));
//                        // 最小套餐与分中心关联表 ComboAndItem
//                        List<Comboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
//                        mdComboanditemService.saOrUpList(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
//                        // 最小套餐与收费项目 ComboAndFzx
//                        List<Comboandfzx> comboandfzxList = orComboandfzxService.getByTcid(oac.getTcid());
//                        mdComboandfzxService.saOrUpList(mapperFacade.mapAsList(comboandfzxList, MdComboandfzx.class));
//                    }
//                }
//                // 收费项目 Items更新过了
//                log.info("订单号:{}导入成功", ddh);
//            }
//        }
//
//        //导入体检者任务等信息
//        List<String> taskIds = peisorgreservations.stream()
//                .map(Peisorgreservation::getId)
//                .collect(Collectors.toList());
//        for (String taskId : taskIds) {
//            //体检者任务分组 md_peisorgreservationgroup
//            List<Peisorgreservationgroup> peisorgreservationgroups = orPeisorgreservationgroupService.getGroupList(taskId);
//            mdPeisorgreservationgroupService.saOrUpList(mapperFacade.mapAsList(peisorgreservationgroups, MdPeisorgreservationgroup.class));
//            for (Peisorgreservationgroup peisorgreservationgroup : peisorgreservationgroups) {
//                //体检者表 md_peispatient
//                String groupId = peisorgreservationgroup.getId();
//                List<Peispatient> peispatients = orPeispatientService.getByGroupId(groupId);
//                mdPeispatientService.saOrUpList(mapperFacade.mapAsList(peispatients, MdPeispatient.class));
//                for (Peispatient peispatient : peispatients) {
//                    //体检者检查项目 md_peispatientfeeitem
//                    List<Peispatientfeeitem> peispatientfeeitems = orPeispatientfeeitemService.getByPatientCode(peispatient.getPatientcode());
//                    mdPeispatientfeeitemService.saOrUpList(mapperFacade.mapAsList(peispatientfeeitems, MdPeispatientfeeitem.class));
//                    //体检者费用主表 md_peispatient_charge_main
//                    PeispatientChargeMain peispatientChargeMain = orPeispatientChargeMainService.getByPatientCode(peispatient.getPatientcode());
//                    mdPeispatientChargeMainService.saOrUp(mapperFacade.map(peispatientChargeMain, MdPeispatientChargeMain.class));
//                }
//            }
//            log.info("任务id:{}导入成功", taskId);
//        }
//        return true;
//    }
}
