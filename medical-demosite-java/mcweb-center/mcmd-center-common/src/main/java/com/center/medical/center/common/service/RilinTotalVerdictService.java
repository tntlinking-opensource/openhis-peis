package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.center.common.bean.model.RilinTotalVerdict;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * ZJ总检结论词表(TotalVerdict)服务接口
 * @since 2025-03-24 11:21:22
 */
public interface RilinTotalVerdictService extends IService<RilinTotalVerdict> {

    /**
     * 按体检号删除
     * @param patientcodes
     */
    void removeByPatientcodesRilin(@Param("patientcodes") List<String> patientcodes);

    /**
     * 插入（批量）,对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatchRilin(Collection<RilinTotalVerdict> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }

}
