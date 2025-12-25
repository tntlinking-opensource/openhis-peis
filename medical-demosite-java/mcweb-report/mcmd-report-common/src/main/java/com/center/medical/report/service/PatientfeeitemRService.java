package com.center.medical.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.enums.MedicalType;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.report.bean.vo.QjOsVo;

import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-09 09:18:49
 */
public interface PatientfeeitemRService extends IService<Peispatientfeeitem> {

    /**
     * 查询有已检职业项目的科室
     *
     * @param patientCode 体检号
     * @param harmList    危害因素ID集合
     * @param medicalType 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
     * @return
     * @see MedicalType
     */
    List<SectionResultMain> getExamedList(String patientCode, List<String> harmList, String medicalType);

    /**
     * 查询有弃检职业项目的科室
     *
     * @param patientCode 体检号
     * @param harmList    危害因素ID集合
     * @param medicalType 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
     * @return
     * @see MedicalType
     */
    List<QjOsVo> getGiveUpList(String patientCode, List<String> harmList, String medicalType);

    /**
     * 查询所有职业拒检项目，展现在综述和职业阳性结果
     *
     * @param patientCode 体检号
     * @param harmList    危害因素ID集合
     * @param medicalType 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
     * @return
     * @see MedicalType
     */
    List<String> getRejectList(String patientCode, List<String> harmList, String medicalType);


}

