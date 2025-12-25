package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.center.common.bean.dto.AdiconItemDto;
import com.center.medical.pacslis.bean.dto.LisDto;

import java.util.List;

/**
 * QT体检者表(MdPeispatient)服务接口
 *
 * @author ay
 * @since 2023-09-07 14:39:48
 */
public interface AdiconService extends IService<Peispatient> {


    /**
     * 获取艾迪康数据
     * @param patientCode
     * @return
     */
    List<LisDto> getAdicon(String patientCode,String loginid,String password);

    /**
     * 获取艾迪康结果
     * @param patientcode
     * @return
     */
    List<AdiconItemDto> getAdiconList(String patientcode,String loginid,String password);

    /**
     * 获取tap数据
     * @param patientcode
     * @return
     */
    List<LisDto> getTap(String patientcode);
}

