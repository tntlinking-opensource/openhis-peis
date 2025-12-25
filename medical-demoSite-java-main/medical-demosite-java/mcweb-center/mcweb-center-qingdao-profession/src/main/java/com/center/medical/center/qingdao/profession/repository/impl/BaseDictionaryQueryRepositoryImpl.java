package com.center.medical.center.qingdao.profession.repository.impl;

import com.center.medical.center.qingdao.profession.entity.dto.BaseDictionaryDto;
import com.center.medical.center.qingdao.profession.entity.dto.SymptomDictionaryDto;
import com.center.medical.center.qingdao.profession.repository.BaseDictionaryQueryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BaseDictionaryQueryRepositoryImpl implements BaseDictionaryQueryRepository {
    private final JdbcTemplate jdbcTemplate;

    public BaseDictionaryQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BaseDictionaryDto> queryListByClassName(String className) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        stringBuilder.append("BD.DICTIONARY_CODE, ");
        stringBuilder.append("BD.DICTIONARY_TYPE, ");
        stringBuilder.append("BD.DICTIONARY_NAME, ");
        stringBuilder.append("BD.SHANDONG_CODE, ");
        stringBuilder.append("BDM.MEDICAL_ID ");
        stringBuilder.append("from BASE_DICTIONARY BD ");
        stringBuilder.append("         inner join BASE_DICTIONARY_CLASS BDC on BD.DICTIONARY_TYPE = BDC.CLASS_CODE ");
        stringBuilder.append("         inner join BASE_DICTIONARY_MATCH BDM on BDM.DICTIONARY_ID = BD.ID ");
        stringBuilder.append("where BDC.CLASS_NAME = ?");
        String sql = stringBuilder.toString();
        return jdbcTemplate.query(sql, new BaseDictionaryDto(), className);
    }

    @Override
    public List<BaseDictionaryDto> queryListByClassNameWithOutMatch(String className) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select BD.DICTIONARY_CODE, BD.DICTIONARY_TYPE, BD.DICTIONARY_NAME,ifnull(BD.QINGDAO_CODE,BD.SHANDONG_CODE) SHANDONG_CODE, BD.DICTIONARY_CODE as MEDICAL_ID,BD.QINGDAO_CODE ");
        stringBuilder.append("from BASE_DICTIONARY BD ");
        stringBuilder.append("         inner join BASE_DICTIONARY_CLASS BDC on BDC.CLASS_CODE = BD.DICTIONARY_TYPE ");
        stringBuilder.append("where BDC.CLASS_NAME = ?");
        String sql = stringBuilder.toString();
        return jdbcTemplate.query(sql, new BaseDictionaryDto(), className);
    }

    @Override
    public List<BaseDictionaryDto> queryCheckItems() {
        return queryListByClassName("体检项目");
    }

    @Override
    public List<SymptomDictionaryDto> querySymptom() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select BD.DICTIONARY_CODE, ");
        stringBuilder.append("       BD.DICTIONARY_TYPE, ");
        stringBuilder.append("       BD.DICTIONARY_NAME, ");
        stringBuilder.append("       BD.SHANDONG_CODE, ");
        stringBuilder.append("       BDM.MEDICAL_ID, ");
        stringBuilder.append("       OS.SYMPTOM, ");
        stringBuilder.append("       OS.SYMPTOM_CODE ");
        stringBuilder.append("from BASE_DICTIONARY BD ");
        stringBuilder.append("         inner join BASE_DICTIONARY_CLASS BDC on BD.DICTIONARY_TYPE = BDC.CLASS_CODE ");
        stringBuilder.append("         inner join BASE_DICTIONARY_MATCH BDM on BDM.DICTIONARY_ID = BD.ID ");
        stringBuilder.append("         inner join MD_OCCUPATION_SYMPTOM OS ON OS.ID = BDM.MEDICAL_ID ");
        stringBuilder.append("where BDC.CLASS_NAME = '症状'");
        String sql= stringBuilder.toString();
        return jdbcTemplate.query(sql, new SymptomDictionaryDto());
    }

    @Override
    public List<BaseDictionaryDto> queryOccupationalContraindications() {
        return queryListByClassName("职业禁忌症");
    }

    @Override
    public List<BaseDictionaryDto> querySuspectedOccupationalDiseases() {
        return queryListByClassName("疑似职业病");
    }

    @Override
    public List<BaseDictionaryDto> queryUnitOfMeasureList() {
        return queryListByClassName("计量单位");
    }

    @Override
    public List<BaseDictionaryDto> queryMedicalExaminationConclusion() {
        return queryListByClassName("体检结论");
    }

    @Override
    public List<BaseDictionaryDto> queryEconomyType() {
        return queryListByClassNameWithOutMatch("经济类型");
    }

    @Override
    public List<BaseDictionaryDto> queryEnterpriseSize() {
        return queryListByClassNameWithOutMatch("企业规模");
    }

    @Override
    public List<BaseDictionaryDto> queryGender() {
        return queryListByClassName("性别");
    }

    @Override
    public List<BaseDictionaryDto> queryMaritalStatus() {
        return queryListByClassName("婚姻状况");
    }

    @Override
    public List<BaseDictionaryDto> queryOnTheJobStatus() {
        return queryListByClassName("在岗状态");
    }

    @Override
    public List<BaseDictionaryDto> queryTypeOfMedicalExamination() {
        return queryListByClassName("体检类型");
    }

    @Override
    public List<BaseDictionaryDto> queryTypeOfCertificate() {
        return queryListByClassName("证件类型");
    }

    @Override
    public List<BaseDictionaryDto> queryHazards() {
        return queryListByClassName("危害因素");
    }

    @Override
    public List<BaseDictionaryDto> queryChargeItems() {
        return queryListByClassName("收费项目");
    }

}
