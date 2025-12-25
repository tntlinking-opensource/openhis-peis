package com.center.medical.center.qingdao.profession.command;

import com.center.medical.center.qingdao.profession.entity.dto.BaseDictionaryDto;
import com.center.medical.center.qingdao.profession.entity.dto.SymptomDictionaryDto;
import com.center.medical.center.qingdao.profession.entity.persistent.BaseWorktype;
import com.center.medical.center.qingdao.profession.repository.BaseDictionaryQueryRepository;
import com.center.medical.center.qingdao.profession.repository.BaseWorktypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
@Slf4j
public class DictionaryCache {
    private final BaseDictionaryQueryRepository baseDictionaryQueryRepository;
    private final BaseWorktypeRepository baseWorktypeRepository;
    /**
     * 检查项目
     */
    private final Map<String, BaseDictionaryDto> checkItems = new HashMap<>();
    /**
     * 症状
     */
    private final Map<String, SymptomDictionaryDto> symptoms = new HashMap<>();
    /**
     * 职业禁忌症
     */
    private final Map<String, BaseDictionaryDto> occupationalContraindications = new HashMap<>();
    /**
     * 疑似职业病
     */
    private final Map<String, BaseDictionaryDto> suspectedOccupationalDiseases = new HashMap<>();
    /**
     * 剂量单位
     */
    private final Map<String, BaseDictionaryDto> unitOfMeasure = new HashMap<>();
    /**
     * 体检结论
     */
    private final Map<String, BaseDictionaryDto> medicalExaminationConclusion = new HashMap<>();
    /**
     * 经济类型
     */
    private final Map<String, BaseDictionaryDto> economyType = new HashMap<>();
    /**
     * 企业规模
     */
    private final Map<String, BaseDictionaryDto> enterpriseSize = new HashMap<>();
    /**
     * 性别
     */
    private final Map<String, BaseDictionaryDto> gender = new HashMap<>();
    /**
     * 婚姻状态
     */
    private final Map<String, BaseDictionaryDto> maritalStatus = new HashMap<>();
    /**
     * 在岗状态
     */
    private final Map<String, BaseDictionaryDto> jobStatus = new HashMap<>();
    /**
     * 体检类型
     */
    private final Map<String, BaseDictionaryDto> typeOfMedicalExamination = new HashMap<>();
    /**
     * 证件类型
     */
    private final Map<String, BaseDictionaryDto> typeOfCertificate = new HashMap<>();

    /**
     * 危害因素
     * key:体检系统危害因素id   value:匹配表中对应的危害因素数据
     */
    private final Map<String, BaseDictionaryDto> hazards = new HashMap<>();
    /**
     * 收费项目
     */
    private final Map<String, BaseDictionaryDto> chargeItems = new HashMap<>();

    private final Map<String, BaseWorktype> workType = new HashMap<>();
    public DictionaryCache(BaseDictionaryQueryRepository baseDictionaryQueryRepository, BaseWorktypeRepository baseWorktypeRepository) {
        this.baseDictionaryQueryRepository = baseDictionaryQueryRepository;
        this.baseWorktypeRepository = baseWorktypeRepository;
        set();
    }

    public void set() {
        List<BaseWorktype> baseWorktypes = baseWorktypeRepository.findByIsDelete(0);
        for (BaseWorktype baseWorktype : baseWorktypes) {
            workType.put(baseWorktype.getId(), baseWorktype);
        }
        List<BaseDictionaryDto> checkItems = baseDictionaryQueryRepository.queryCheckItems();
        for (BaseDictionaryDto checkItem : checkItems) {
            this.checkItems.put(checkItem.getMedicalId(), checkItem);
        }
        List<SymptomDictionaryDto> symptoms = baseDictionaryQueryRepository.querySymptom();
        for (SymptomDictionaryDto symptom : symptoms) {
            this.symptoms.put(symptom.getSymptom(), symptom);
        }
        List<BaseDictionaryDto> occupationalContraindications = baseDictionaryQueryRepository.queryOccupationalContraindications();
        for (BaseDictionaryDto occupationalContraindication : occupationalContraindications) {
            this.occupationalContraindications.put(occupationalContraindication.getMedicalId(), occupationalContraindication);
        }
        List<BaseDictionaryDto> occupationalDiseases = baseDictionaryQueryRepository.querySuspectedOccupationalDiseases();
        for (BaseDictionaryDto suspectedOccupationalDisease : occupationalDiseases) {
            this.suspectedOccupationalDiseases.put(suspectedOccupationalDisease.getMedicalId(), suspectedOccupationalDisease);
        }
        List<BaseDictionaryDto> unitOfMeasureList = baseDictionaryQueryRepository.queryUnitOfMeasureList();
        for (BaseDictionaryDto unit : unitOfMeasureList) {
            this.unitOfMeasure.put(unit.getMedicalId(), unit);
        }
        List<BaseDictionaryDto> medicalExaminationConclusions = baseDictionaryQueryRepository.queryMedicalExaminationConclusion();
        for (BaseDictionaryDto medicalExaminationConclusion : medicalExaminationConclusions) {
            this.medicalExaminationConclusion.put(medicalExaminationConclusion.getMedicalId(), medicalExaminationConclusion);
        }
        List<BaseDictionaryDto> economyTypes = baseDictionaryQueryRepository.queryEconomyType();
        for (BaseDictionaryDto economyType : economyTypes) {
            this.economyType.put(economyType.getMedicalId(), economyType);
        }
        List<BaseDictionaryDto> enterpriseSizeList = baseDictionaryQueryRepository.queryEnterpriseSize();
        for (BaseDictionaryDto enterpriseSize : enterpriseSizeList) {
            this.enterpriseSize.put(enterpriseSize.getMedicalId(), enterpriseSize);
        }
        List<BaseDictionaryDto> genders = baseDictionaryQueryRepository.queryGender();
        for (BaseDictionaryDto gender : genders) {
            this.gender.put(gender.getMedicalId(), gender);
        }
        List<BaseDictionaryDto> maritalStatusList = baseDictionaryQueryRepository.queryMaritalStatus();
        for (BaseDictionaryDto maritalStatus : maritalStatusList) {
            this.maritalStatus.put(maritalStatus.getMedicalId(), maritalStatus);
        }
        List<BaseDictionaryDto> theJobStatusList = baseDictionaryQueryRepository.queryOnTheJobStatus();
        for (BaseDictionaryDto jobStatus : theJobStatusList) {
            this.jobStatus.put(jobStatus.getMedicalId(), jobStatus);
        }
        List<BaseDictionaryDto> typeOfMedicalExaminations = baseDictionaryQueryRepository.queryTypeOfMedicalExamination();
        for (BaseDictionaryDto typeOfMedicalExamination : typeOfMedicalExaminations) {
            this.typeOfMedicalExamination.put(typeOfMedicalExamination.getMedicalId(), typeOfMedicalExamination);
        }
        List<BaseDictionaryDto> certificates = baseDictionaryQueryRepository.queryTypeOfCertificate();
        for (BaseDictionaryDto certificate : certificates) {
            this.typeOfCertificate.put(certificate.getMedicalId(), certificate);
        }
        List<BaseDictionaryDto> hazards = baseDictionaryQueryRepository.queryHazards();
        for (BaseDictionaryDto hazard : hazards) {
            this.hazards.put(hazard.getMedicalId(), hazard);
        }
        List<BaseDictionaryDto> chargeItems = baseDictionaryQueryRepository.queryChargeItems();
        for (BaseDictionaryDto chargeItem : chargeItems) {
            this.chargeItems.put(chargeItem.getMedicalId(), chargeItem);
        }
    }

    /**
     * 用体检系统危害因素id获取匹配表中的危害因素数据
     * @param jhy 体检系统危害因素id
     * @return
     */
    public BaseDictionaryDto getHazards(String jhy) {
        return hazards.getOrDefault(jhy,null);
    }

    public BaseDictionaryDto getConclusionCode(String occupationDiagnosis) {
        return medicalExaminationConclusion.getOrDefault(occupationDiagnosis, null);
    }

    public BaseDictionaryDto getExamItem(String itemId) {
        return chargeItems.getOrDefault(itemId, null);
    }

    public BaseDictionaryDto getCheckItem(String examId) {
        return checkItems.getOrDefault(examId, null);
    }

    public BaseDictionaryDto getEconomyType(String type) {
        return economyType.getOrDefault(type, null);
    }

    public BaseDictionaryDto getDiseases(String code) {
        return suspectedOccupationalDiseases.getOrDefault(code, null);
    }

    public SymptomDictionaryDto getSymptom(String name) {
        return symptoms.getOrDefault(name, null);
    }

    public BaseWorktype getWorktype(String type) {
        return workType.getOrDefault(type, null);
    }
}