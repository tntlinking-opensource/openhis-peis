package com.center.medical.center.qingdao.profession.entity.enumerate;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DepartmentCodeType {
    PREVENTIVE_HEALTH_CARE("A01", "预防保健科"),
    GENERAL_MEDICINE("A02", "全科医疗科"),
    INTERNAL_MEDICINE("A03", "内科"),
    BREATHE_INTERNAL_MEDICINE("A03.01", "呼吸内科专业"),
    DIGEST_INTERNAL_MEDICINE("A03.02", "消化内科专业"),
    NERVE_INTERNAL_MEDICINE("A03.03", "神经内科专业"),
    CARDIOVASCULAR_INTERNAL_MEDICINE("A03.04", "心血管内科专业"),
    BLOOD_INTERNAL_MEDICINE("A03.05", "血液内科专业"),
    NEPHROLOGY_MEDICINE("A03.06", "肾病学专业"),
    ENDOCRINE_MEDICINE("A03.07", "内分泌专业"),
    IMMUNOLOGY_MEDICINE("A03.08", "免疫学专业"),
    ALLERGY_MEDICINE("A03.08", "免疫学专业"),
    OLD_MEDICINE("A03.10", "老年病专业"),
    OTHER("A03.11", "其他"),
    SURGICAL("A04", "外科"),
    GENERAL_SURGERY("A04.01", "普通外科专业"),
    LIVER_TRANSPLANT("A04.01.01", "肝脏移植项目"),
    PANCREAS_TRANSPLANTATION("A04.01.02", "胰腺移植项目"),
    SMALL_BOWEL_TRANSPLANT("A04.01.03", "小肠移植项目"),
    NEUROSURGERY("A04.02", "神经外科专业"),
    ORTHOPEDICS("A04.03", "骨科专业"),
    UROLOGY("A04.04", "泌尿外科专业"),
    KIDNEY_TRANSPLANT("A04.04.01", "肾脏移植项目"),

    ;
    private final String value;
    private final String name;

    public static DepartmentCodeType get(String value) {
        return EnumUtil.likeValueOf(DepartmentCodeType.class, value);
    }

    public static String getName(String name) {
        return Optional.ofNullable(get(name)).map(DepartmentCodeType::getName).orElse(null);
    }

    public static String getValue(String value) {
        return Optional.ofNullable(get(value)).map(DepartmentCodeType::getValue).orElse(null);
    }
}