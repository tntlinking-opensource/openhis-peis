package com.center.medical.pacslis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.core.domain.R;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.sellcrm.bean.model.Comboexamitem;

import java.util.List;
import java.util.Map;

/**
 * @author xhp
 * @since 2023-02-20 9:04
 */
public interface LisInterfaceService extends IService<Peispatientexamitem> {

    /**
     * 获取Lis结果数据
     *
     * @param patientcode
     */
    R<String> receive(String patientcode);

    /**
     * 保存lis结果
     *
     * @param patientcode 体检号
     * @param lisDtoList  lis结果
     */
    void save(String patientcode, List<LisDto> lisDtoList);

    /**
     * 获取体检者信息，并检查体检者状态是否可以修改科室结果
     *
     * @param patientcode
     * @return
     */
    Peispatient checkPeispatient(String patientcode);

    /**
     * @param jhys
     * @param medicaltype
     * @return
     */
    Map<String, List<Comboexamitem>> getComboexamitems(String jhys, String medicaltype, String tjlx);

    /**
     * 获取艾迪康结果
     * @param patientcode
     * @return
     */
    R getAdiconList(String patientcode);

    /**
     * 获取Tap结果
     * @param patientcode
     * @return
     */
    R getTap(String patientcode);
}
