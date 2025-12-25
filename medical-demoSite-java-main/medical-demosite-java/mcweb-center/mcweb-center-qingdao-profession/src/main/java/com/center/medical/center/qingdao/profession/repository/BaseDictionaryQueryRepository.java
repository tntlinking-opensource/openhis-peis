package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.BaseDictionaryDto;
import com.center.medical.center.qingdao.profession.entity.dto.SymptomDictionaryDto;

import java.util.List;

public interface BaseDictionaryQueryRepository {
    /**
     * 获取指定数据字典列表
     *
     * @param className 数据字典名称
     * @return 数据字典列表
     */
    List<BaseDictionaryDto> queryListByClassName(String className);

    List<BaseDictionaryDto> queryListByClassNameWithOutMatch(String className);

    /**
     * 检查项目列表
     *
     * @return 检查项目列表
     */
    List<BaseDictionaryDto> queryCheckItems();

    /**
     * 症状列表
     *
     * @return 症状列表
     */
    List<SymptomDictionaryDto> querySymptom();

    /**
     * 职业禁忌症
     *
     * @return 职业禁忌症
     */
    List<BaseDictionaryDto> queryOccupationalContraindications();

    /**
     * 疑似职业病
     *
     * @return 疑似职业病
     */
    List<BaseDictionaryDto> querySuspectedOccupationalDiseases();

    /**
     * 计量单位
     *
     * @return 计量单位
     */
    List<BaseDictionaryDto> queryUnitOfMeasureList();

    /**
     * 体检结论
     *
     * @return 体检结论
     */
    List<BaseDictionaryDto> queryMedicalExaminationConclusion();

    /**
     * 经济类型
     *
     * @return 经济类型
     */
    List<BaseDictionaryDto> queryEconomyType();

    /**
     * 企业规模
     *
     * @return 企业规模
     */
    List<BaseDictionaryDto> queryEnterpriseSize();

    /**
     * 性别
     *
     * @return 性别
     */
    List<BaseDictionaryDto> queryGender();

    /**
     * 婚姻状态
     *
     * @return 婚姻状态
     */
    List<BaseDictionaryDto> queryMaritalStatus();

    /**
     * 在岗状态
     *
     * @return 在岗状态
     */
    List<BaseDictionaryDto> queryOnTheJobStatus();

    /**
     * 体检类型
     *
     * @return 体检类型
     */
    List<BaseDictionaryDto> queryTypeOfMedicalExamination();

    /**
     * 证件类型
     *
     * @return 证件类型
     */
    List<BaseDictionaryDto> queryTypeOfCertificate();

    /**
     * 危害因素
     *
     * @return 危害因素
     */
    List<BaseDictionaryDto> queryHazards();

    /**
     * 收费项目
     *
     * @return 收费项目
     */
    List<BaseDictionaryDto> queryChargeItems();
}