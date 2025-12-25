package com.center.medical.center.qingdao.profession.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class RDataDTO {

    /**
     * type
     */
    @JsonProperty("type")
    private String type;
    /**
     * data
     */
    @JsonProperty("data")
    private List<DataDTO> data;

    /**
     * DataDTO
     */
    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * documentid
         */
        @JsonProperty("DOCUMENTID")
        private String documentid;
        /**
         * phyexamname
         */
        @JsonProperty("PHYEXAMNAME")
        private String phyexamname;
        /**
         * phyexamcode
         */
        @JsonProperty("PHYEXAMCODE")
        private String phyexamcode;
        /**
         * phyexamaddressName
         */
        @JsonProperty("PHYEXAMADDRESS_NAME")
        private String phyexamaddressName;
        /**
         * phyexamaddressCode
         */
        @JsonProperty("PHYEXAMADDRESS_CODE")
        private String phyexamaddressCode;
        /**
         * physicaltypes
         */
        @JsonProperty("PHYSICALTYPES")
        private String physicaltypes;
        /**
         * examDate
         */
        @JsonProperty("EXAM_DATE")
        private String examDate;
        /**
         * examNum
         */
        @JsonProperty("EXAM_NUM")
        private String examNum;
        /**
         * workerName
         */
        @JsonProperty("WORKER_NAME")
        private String workerName;
        /**
         * idCardTypeCode
         */
        @JsonProperty("ID_CARD_TYPE_CODE")
        private String idCardTypeCode;
        /**
         * idCard
         */
        @JsonProperty("ID_CARD")
        private String idCard;
        /**
         * genderCode
         */
        @JsonProperty("GENDER_CODE")
        private String genderCode;
        /**
         * birthDate
         */
        @JsonProperty("BIRTH_DATE")
        private String birthDate;
        /**
         * age
         */
        @JsonProperty("AGE")
        private String age;
        /**
         * birthplace
         */
        @JsonProperty("BIRTHPLACE")
        private String birthplace;
        /**
         * nation
         */
        @JsonProperty("NATION")
        private String nation;
        /**
         * nationCode
         */
        @JsonProperty("NATION_CODE")
        private String nationCode;
        /**
         * maritalStatusName
         */
        @JsonProperty("MARITAL_STATUS_NAME")
        private String maritalStatusName;
        /**
         * maritalStatusCode
         */
        @JsonProperty("MARITAL_STATUS_CODE")
        private String maritalStatusCode;
        /**
         * edBgName
         */
        @JsonProperty("ED_BG_NAME")
        private String edBgName;
        /**
         * edBgCode
         */
        @JsonProperty("ED_BG_CODE")
        private String edBgCode;
        /**
         * workerTelphone
         */
        @JsonProperty("WORKER_TELPHONE")
        private String workerTelphone;
        /**
         * information
         */
        @JsonProperty("INFORMATION")
        private String information;
        /**
         * emergencyContact
         */
        @JsonProperty("EMERGENCY_CONTACT")
        private String emergencyContact;
        /**
         * contactInformation
         */
        @JsonProperty("CONTACT_INFORMATION")
        private String contactInformation;
        /**
         * homeAddress
         */
        @JsonProperty("HOME_ADDRESS")
        private String homeAddress;
        /**
         * smokeing
         */
        @JsonProperty("SMOKEING")
        private String smokeing;
        /**
         * smokeNumber
         */
        @JsonProperty("SMOKE_NUMBER")
        private String smokeNumber;
        /**
         * smokeYear
         */
        @JsonProperty("SMOKE_YEAR")
        private String smokeYear;
        /**
         * drinking
         */
        @JsonProperty("DRINKING")
        private String drinking;
        /**
         * drinkNumber
         */
        @JsonProperty("DRINK_NUMBER")
        private String drinkNumber;
        /**
         * drinkYear
         */
        @JsonProperty("DRINK_YEAR")
        private String drinkYear;
        /**
         * menstrMenarche
         */
        @JsonProperty("MENSTR_MENARCHE")
        private String menstrMenarche;
        /**
         * menstrPeriod
         */
        @JsonProperty("MENSTR_PERIOD")
        private String menstrPeriod;
        /**
         * menstrCycle
         */
        @JsonProperty("MENSTR_CYCLE")
        private String menstrCycle;
        /**
         * menstrMenopausalAge
         */
        @JsonProperty("MENSTR_MENOPAUSAL_AGE")
        private String menstrMenopausalAge;
        /**
         * birthChildNumber
         */
        @JsonProperty("BIRTH_CHILD_NUMBER")
        private String birthChildNumber;
        /**
         * birthAbortionNumber
         */
        @JsonProperty("BIRTH__ABORTION_NUMBER")
        private String birthAbortionNumber;
        /**
         * birthPrematureNumber
         */
        @JsonProperty("BIRTH__PREMATURE_NUMBER")
        private String birthPrematureNumber;
        /**
         * birthStillbirthNumber
         */
        @JsonProperty("BIRTH__STILLBIRTH_NUMBER")
        private String birthStillbirthNumber;
        /**
         * birthAbnormalNumber
         */
        @JsonProperty("BIRTH__ABNORMAL_NUMBER")
        private String birthAbnormalNumber;
        /**
         * acohDiseaseName
         */
        @JsonProperty("ACOH_DISEASE_NAME")
        private String acohDiseaseName;
        /**
         * acohDiagnosisTime
         */
        @JsonProperty("ACOH_DIAGNOSIS_TIME")
        private String acohDiagnosisTime;
        /**
         * acohDiagnosisUnit
         */
        @JsonProperty("ACOH_DIAGNOSIS_UNIT")
        private String acohDiagnosisUnit;
        /**
         * acohCured
         */
        @JsonProperty("ACOH_CURED")
        private String acohCured;
        /**
         * pastHistory
         */
        @JsonProperty("PAST_HISTORY")
        private String pastHistory;
        /**
         * otherPastHistory
         */
        @JsonProperty("OTHER_PAST_HISTORY")
        private String otherPastHistory;
        /**
         * familyHistory
         */
        @JsonProperty("FAMILY_HISTORY")
        private String familyHistory;
        /**
         * otherFamilyHistory
         */
        @JsonProperty("OTHER_FAMILY_HISTORY")
        private String otherFamilyHistory;
        /**
         * marriageDate
         */
        @JsonProperty("MARRIAGE_DATE")
        private String marriageDate;
        /**
         * spouseExposureRadiation
         */
        @JsonProperty("SPOUSE_EXPOSURE_RADIATION")
        private String spouseExposureRadiation;
        /**
         * spouseOccu
         */
        @JsonProperty("SPOUSE_OCCU")
        private String spouseOccu;
        /**
         * lifeHistory
         */
        @JsonProperty("LIFE_HISTORY")
        private String lifeHistory;
        /**
         * familyHistoryRadiation
         */
        @JsonProperty("FAMILY_HISTORY_RADIATION")
        private String familyHistoryRadiation;
        /**
         * other
         */
        @JsonProperty("OTHER")
        private String other;
        /**
         * examTypeCode
         */
        @JsonProperty("EXAM_TYPE_CODE")
        private String examTypeCode;
        /**
         * factorCode
         */
        @JsonProperty("FACTOR_CODE")
        private String factorCode;
        /**
         * factorOther
         */
        @JsonProperty("FACTOR_OTHER")
        private String factorOther;
        /**
         * enterpriseName
         */
        @JsonProperty("ENTERPRISE_NAME")
        private String enterpriseName;
        /**
         * creditCode
         */
        @JsonProperty("CREDIT_CODE")
        private String creditCode;
        /**
         * economicTypeCode
         */
        @JsonProperty("ECONOMIC_TYPE_CODE")
        private String economicTypeCode;
        /**
         * industryCategoryCode
         */
        @JsonProperty("INDUSTRY_CATEGORY_CODE")
        private String industryCategoryCode;
        /**
         * businessScaleCode
         */
        @JsonProperty("BUSINESS_SCALE_CODE")
        private String businessScaleCode;
        /**
         * addressName
         */
        @JsonProperty("ADDRESS_NAME")
        private String addressName;
        /**
         * addressCode
         */
        @JsonProperty("ADDRESS_CODE")
        private String addressCode;
        /**
         * addressDetail
         */
        @JsonProperty("ADDRESS_DETAIL")
        private String addressDetail;
        /**
         * addressZipCode
         */
        @JsonProperty("ADDRESS_ZIP_CODE")
        private String addressZipCode;
        /**
         * enterpriseContact
         */
        @JsonProperty("ENTERPRISE_CONTACT")
        private String enterpriseContact;
        /**
         * contactTelphone
         */
        @JsonProperty("CONTACT_TELPHONE")
        private String contactTelphone;
        /**
         * allName
         */
        @JsonProperty("ALL_NAME")
        private String allName;
        /**
         * enterpriseNameEmployer
         */
        @JsonProperty("ENTERPRISE_NAME_EMPLOYER")
        private String enterpriseNameEmployer;
        /**
         * creditCodeEmployer
         */
        @JsonProperty("CREDIT_CODE_EMPLOYER")
        private String creditCodeEmployer;
        /**
         * econocTypeCodeEmoyer
         */
        @JsonProperty("ECONOC_TYPE_CODE_EMOYER")
        private String econocTypeCodeEmoyer;
        /**
         * indtryCatryCodeEmoyer
         */
        @JsonProperty("INDTRY_CATRY_CODE_EMOYER")
        private String indtryCatryCodeEmoyer;
        /**
         * busssScaleCodeEmoyer
         */
        @JsonProperty("BUSSS_SCALE_CODE_EMOYER")
        private String busssScaleCodeEmoyer;
        /**
         * addressCodeEmployer
         */
        @JsonProperty("ADDRESS_CODE_EMPLOYER")
        private String addressCodeEmployer;
        /**
         * allNameEmployer
         */
        @JsonProperty("ALL_NAME_EMPLOYER")
        private String allNameEmployer;
        /**
         * zyjjzName
         */
        @JsonProperty("ZYJJZ_NAME")
        private String zyjjzName;
        /**
         * writePerson
         */
        @JsonProperty("WRITE_PERSON")
        private String writePerson;
        /**
         * writePersonTelphone
         */
        @JsonProperty("WRITE_PERSON_TELPHONE")
        private String writePersonTelphone;
        /**
         * writeDate
         */
        @JsonProperty("WRITE_DATE")
        private String writeDate;
        /**
         * reportOrganCreditCode
         */
        @JsonProperty("REPORT_ORGAN_CREDIT_CODE")
        private String reportOrganCreditCode;
        /**
         * remark
         */
        @JsonProperty("REMARK")
        private String remark;
        /**
         * workType
         */
        @JsonProperty("WORK_TYPE")
        private String workType;
        /**
         * workTypeCode
         */
        @JsonProperty("WORK_TYPE_CODE")
        private String workTypeCode;
        /**
         * jcType
         */
        @JsonProperty("JC_TYPE")
        private String jcType;
        /**
         * otherWorkType
         */
        @JsonProperty("OTHER_WORK_TYPE")
        private String otherWorkType;
        /**
         * harmStartDate
         */
        @JsonProperty("HARM_START_DATE")
        private String harmStartDate;
        /**
         * harmAgeYear
         */
        @JsonProperty("HARM_AGE_YEAR")
        private String harmAgeYear;
        /**
         * harmAgeMonth
         */
        @JsonProperty("HARM_AGE_MONTH")
        private String harmAgeMonth;
        /**
         * isReview
         */
        @JsonProperty("IS_REVIEW")
        private String isReview;
        /**
         * reportPerson
         */
        @JsonProperty("REPORT_PERSON")
        private String reportPerson;
        /**
         * reportPersonTel
         */
        @JsonProperty("REPORT_PERSON_TEL")
        private String reportPersonTel;
        /**
         * reportDate
         */
        @JsonProperty("REPORT_DATE")
        private String reportDate;
        /**
         * contactFactorCode
         */
        @JsonProperty("CONTACT_FACTOR_CODE")
        private String contactFactorCode;
        /**
         * contactFactorOther
         */
        @JsonProperty("CONTACT_FACTOR_OTHER")
        private String contactFactorOther;
        /**
         * examSummary
         */
        @JsonProperty("EXAM_SUMMARY")
        private String examSummary;
        /**
         * examAdvice
         */
        @JsonProperty("EXAM_ADVICE")
        private String examAdvice;
        /**
         * zjDocName
         */
        @JsonProperty("ZJ_DOC_NAME")
        private String zjDocName;
        /**
         * zjDocIdNum
         */
        @JsonProperty("ZJ_DOC_ID_NUM")
        private String zjDocIdNum;
        /**
         * systemTime
         */
        @JsonProperty("SYSTEM_TIME")
        private String systemTime;
        /**
         * operatetype
         */
        @JsonProperty("OPERATETYPE")
        private String operatetype;
        /**
         * 1：现在每天吸
         * 2：现在吸，但不是每天吸
         * 3：过去吸，现在不吸
         * 4：从不吸
         */
        @JsonProperty("smokingStatus")
        private String smokingStatus;
        /**
         * 吸烟史-年
         */
        @JsonProperty("personalHistoryYear")
        private String personalHistoryYear;
        /**
         * 吸烟史-月，小于等于 11 的整数
         */
        @JsonProperty("personalHistoryMonth")
        private String personalHistoryMonth;
        /**
         * 平均每天吸烟量
         */
        @JsonProperty("dailySmokingVolume")
        private String dailySmokingVolume;
        /**
         * examConclusionList
         */
        @JsonProperty("EXAM_CONCLUSION_LIST")
        private List<EXAMCONCLUSIONLISTDTO> examConclusionList;
        /**
         * examItemResult
         */
        @JsonProperty("EXAM_ITEM_RESULT")
        private List<EXAMITEMRESULTDTO> examItemResult;
        /**
         * examItemResultList
         */
        @JsonProperty("EXAM_ITEM_RESULT_LIST")
        private List<EXAMITEMRESULTLISTDTO> examItemResultList;
        /**
         * careerHistoryList
         */
        @JsonProperty("CAREER_HISTORY_LIST")
        private List<CAREERHISTORYLISTDTO> careerHistoryList;
        /**
         * pastMedicalHistoryList
         */
        @JsonProperty("PAST_MEDICAL_HISTORY_LIST")
        private List<PASTMEDICALHISTORYLISTDTO> pastMedicalHistoryList;

        /**
         * EXAMCONCLUSIONLISTDTO
         */
        @NoArgsConstructor
        @Data
        public static class EXAMCONCLUSIONLISTDTO {
            /**
             * documentid
             */
            @JsonProperty("DOCUMENTID")
            private String documentid;
            /**
             * phyexamname
             */
            @JsonProperty("PHYEXAMNAME")
            private String phyexamname;
            /**
             * phyexamcode
             */
            @JsonProperty("PHYEXAMCODE")
            private String phyexamcode;
            /**
             * examNum
             */
            @JsonProperty("EXAM_NUM")
            private String examNum;
            /**
             * itamCode
             */
            @JsonProperty("ITAM_CODE")
            private String itamCode;
            /**
             * itamName
             */
            @JsonProperty("ITAM_NAME")
            private String itamName;
            /**
             * examConclusionCode
             * 01目前未见异常 、02复查、03疑似职业病 、04职业禁忌证 、05其他疾病或异常
             */
            @JsonProperty("EXAM_CONCLUSION_CODE")
            private String examConclusionCode;
            /**
             * yszybCode
             */
            @JsonProperty("YSZYB_CODE")
            private String yszybCode = "无";
            /**
             * zyjjzName
             */
            @JsonProperty("ZYJJZ_NAME")
            private String zyjjzName = "无";
            /**
             * qtjbName
             */
            @JsonProperty("QTJB_NAME")
            private String qtjbName = "无";
            /**
             * systemTime
             */
            @JsonProperty("SYSTEM_TIME")
            private String systemTime;
            /**
             * operatetype
             */
            @JsonProperty("OPERATETYPE")
            private String operatetype;
        }

        /**
         * EXAMITEMRESULTDTO
         */
        @NoArgsConstructor
        @Data
        public static class EXAMITEMRESULTDTO {
            /**
             * documentid
             */
            @JsonProperty("DOCUMENTID")
            private String documentid;
            /**
             * phyexamname
             */
            @JsonProperty("PHYEXAMNAME")
            private String phyexamname;
            /**
             * phyexamcode
             */
            @JsonProperty("PHYEXAMCODE")
            private String phyexamcode;
            /**
             * examNum
             */
            @JsonProperty("EXAM_NUM")
            private String examNum;
            /**
             * itamCode
             */
            @JsonProperty("ITAM_CODE")
            private String itamCode;
            /**
             * examItemPname
             */
            @JsonProperty("EXAM_ITEM_PNAME")
            private String examItemPname;
            /**
             * examItemPcode
             */
            @JsonProperty("EXAM_ITEM_PCODE")
            private String examItemPcode;
            /**
             * deptName
             */
            @JsonProperty("DEPT_NAME")
            private String deptName;
            /**
             * deptCode
             */
            @JsonProperty("DEPT_CODE")
            private String deptCode;
            /**
             * docName
             */
            @JsonProperty("DOC_NAME")
            private String docName;
            /**
             * docIdcard
             */
            @JsonProperty("DOC_IDCARD")
            private String docIdcard;
            /**
             * summary
             */
            @JsonProperty("SUMMARY")
            private String summary;
            /**
             * systemTime
             */
            @JsonProperty("SYSTEM_TIME")
            private String systemTime;
            /**
             * operatetype
             */
            @JsonProperty("OPERATETYPE")
            private String operatetype;
            /**
             * imgList
             */
            @JsonProperty("IMG_LIST")
            private List<IMGLISTDTO> imgList = new ArrayList<>(0);

            /**
             * IMGLISTDTO
             */
            @NoArgsConstructor
            @Data
            public static class IMGLISTDTO {
                /**
                 * imgpath
                 */
                @JsonProperty("IMGPATH")
                private String imgpath;
                /**
                 * img
                 */
                @JsonProperty("IMG")
                private String img;
            }
        }

        /**
         * EXAMITEMRESULTLISTDTO
         */
        @NoArgsConstructor
        @Data
        public static class EXAMITEMRESULTLISTDTO {
            /**
             * documentid
             */
            @JsonProperty("DOCUMENTID")
            private String documentid;
            /**
             * phyexamname
             */
            @JsonProperty("PHYEXAMNAME")
            private String phyexamname;
            /**
             * phyexamcode
             */
            @JsonProperty("PHYEXAMCODE")
            private String phyexamcode;
            /**
             * examNum
             */
            @JsonProperty("EXAM_NUM")
            private String examNum;
            /**
             * examItemPname
             */
            @JsonProperty("EXAM_ITEM_PNAME")
            private String examItemPname;
            /**
             * examItemPcode
             */
            @JsonProperty("EXAM_ITEM_PCODE")
            private String examItemPcode;
            /**
             * examItemName
             */
            @JsonProperty("EXAM_ITEM_NAME")
            private String examItemName;
            /**
             * examItemCode
             */
            @JsonProperty("EXAM_ITEM_CODE")
            private String examItemCode;
            /**
             * hsExamItemName
             */
            @JsonProperty("HS_EXAM_ITEM_NAME")
            private String hsExamItemName;
            /**
             * hsExamItemCode
             */
            @JsonProperty("HS_EXAM_ITEM_CODE")
            private String hsExamItemCode;
            /**
             * examResultType
             */
            @JsonProperty("EXAM_RESULT_TYPE")
            private String examResultType;
            /**
             * examResult
             */
            @JsonProperty("EXAM_RESULT")
            private String examResult;
            /**
             * examItemUnitCode
             */
            @JsonProperty("EXAM_ITEM_UNIT_CODE")
            private String examItemUnitCode = "%";
            /**
             * referenceRangeMin
             */
            @JsonProperty("REFERENCE_RANGE_MIN")
            private String referenceRangeMin;// = "无";
            /**
             * referenceRangeMax
             */
            @JsonProperty("REFERENCE_RANGE_MAX")
            private String referenceRangeMax;// = "无";
            /**
             * abnormal
             */
            @JsonProperty("ABNORMAL")
            private String abnormal = "3";
            /**
             * systemTime
             */
            @JsonProperty("SYSTEM_TIME")
            private String systemTime;
            /**
             * operatetype
             */
            @JsonProperty("OPERATETYPE")
            private String operatetype;
        }

        /**
         * CAREERHISTORYLISTDTO
         */
        @NoArgsConstructor
        @Data
        public static class CAREERHISTORYLISTDTO {
            /**
             * documentid
             */
            @JsonProperty("DOCUMENTID")
            private String documentid;
            /**
             * phyexamname
             */
            @JsonProperty("PHYEXAMNAME")
            private String phyexamname;
            /**
             * phyexamcode
             */
            @JsonProperty("PHYEXAMCODE")
            private String phyexamcode;
            /**
             * examNum
             */
            @JsonProperty("EXAM_NUM")
            private String examNum;
            /**
             * sEndTime
             */
            @JsonProperty("S_END_TIME")
            private String sEndTime;
            /**
             * workUnit
             */
            @JsonProperty("WORK_UNIT")
            private String workUnit;
            /**
             * workshop
             */
            @JsonProperty("WORKSHOP")
            private String workshop;
            /**
             * workType
             */
            @JsonProperty("WORK_TYPE")
            private String workType;
            /**
             * workTypeCode
             */
            @JsonProperty("WORK_TYPE_CODE")
            private String workTypeCode;
            /**
             * itamName
             */
            @JsonProperty("ITAM_NAME")
            private String itamName;
            /**
             * itamCode
             */
            @JsonProperty("ITAM_CODE")
            private String itamCode;
            /**
             * protectiveMeasures
             */
            @JsonProperty("PROTECTIVE_MEASURES")
            private String protectiveMeasures;
            /**
             * operatetype
             */
            @JsonProperty("OPERATETYPE")
            private String operatetype;
            /**
             * radiationType
             */
            @JsonProperty("RADIATION_TYPE")
            private String radiationType;
            /**
             * dailyWorkload
             */
            @JsonProperty("DAILY_WORKLOAD")
            private String dailyWorkload;
            /**
             * cumulativeExposure
             */
            @JsonProperty("CUMULATIVE_EXPOSURE")
            private String cumulativeExposure;
            /**
             * excessiveExposure
             */
            @JsonProperty("EXCESSIVE_EXPOSURE")
            private String excessiveExposure;
            /**
             * remarks
             */
            @JsonProperty("REMARKS")
            private String remarks;
            /**
             * systemTime
             */
            @JsonProperty("SYSTEM_TIME")
            private String systemTime;
        }

        /**
         * PASTMEDICALHISTORYLISTDTO
         */
        @NoArgsConstructor
        @Data
        public static class PASTMEDICALHISTORYLISTDTO {
            /**
             * documentid
             */
            @JsonProperty("DOCUMENTID")
            private String documentid;
            /**
             * phyexamname
             */
            @JsonProperty("PHYEXAMNAME")
            private String phyexamname;
            /**
             * phyexamcode
             */
            @JsonProperty("PHYEXAMCODE")
            private String phyexamcode;
            /**
             * examNum
             */
            @JsonProperty("EXAM_NUM")
            private String examNum;
            /**
             * acohDiseaseName
             */
            @JsonProperty("ACOH_DISEASE_NAME")
            private String acohDiseaseName;
            /**
             * acohDiagnosisTime
             */
            @JsonProperty("ACOH_DIAGNOSIS_TIME")
            private String acohDiagnosisTime;
            /**
             * acohDiagnosisUnit
             */
            @JsonProperty("ACOH_DIAGNOSIS_UNIT")
            private String acohDiagnosisUnit;
            /**
             * treatmentProcess
             */
            @JsonProperty("TREATMENT_PROCESS")
            private String treatmentProcess;
            /**
             * outcome
             */
            @JsonProperty("OUTCOME")
            private String outcome;
            /**
             * systemTime
             */
            @JsonProperty("SYSTEM_TIME")
            private String systemTime;
        }
    }
}
