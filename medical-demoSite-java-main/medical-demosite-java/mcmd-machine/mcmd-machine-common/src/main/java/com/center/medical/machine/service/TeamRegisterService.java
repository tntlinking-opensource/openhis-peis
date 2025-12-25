package com.center.medical.machine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.machine.bean.dto.PeispatientDto;
import com.center.medical.machine.bean.model.RegisteredEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
public interface TeamRegisterService extends IService<Peispatient> {

    /**
     * 获取体检者数据团体
     * @param entity
     * @return
     */
    List<PeispatientDto> getPeispatient(RegisteredEntity entity);

    /**
     * 获取体检者数据个人
     * @param entity
     * @return
     */
    List<PeispatientDto> getPeispatientPersonal(RegisteredEntity entity);
    /**
     * 获取体检者数据复查
     * @param entity
     * @return
     */
    List<PeispatientDto> getPeispatientRecheck(RegisteredEntity entity);
    /**
     * 获取体检者数据补检
     * @param entity
     * @return
     */
    List<PeispatientDto> getPeispatientMakeUp(RegisteredEntity entity);

    /**
     * 获取价格
     * @param patientCode
     * @return
     */
    BigDecimal getPriceAmount(String patientCode);

    /**
     * 创建体检者
     * @param registeredEntity
     * @return
     */
    RegisteredEntity createPeispatient(RegisteredEntity registeredEntity);
}

