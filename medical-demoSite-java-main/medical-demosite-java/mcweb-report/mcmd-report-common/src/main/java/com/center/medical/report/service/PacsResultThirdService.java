package com.center.medical.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.config.YiyingConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.report.bean.model.PacsResultThird;

import java.util.List;

/**
 * 易影报告回传(PacsResultThird)服务接口
 *
 * @author makejava
 * @since 2025-08-14 10:15:33
 */
public interface PacsResultThirdService extends IService<PacsResultThird> {
    /**
     * 获取deps中最后一个有易影结果的科室编号
     * @param patientcode
     * @param deps
     * @return
     */
    String getLastYiyingDeptNo(String patientcode, List<SysDept> deps);

    /**
     * 生成易影云胶片二维码base64
     * @param patientcode
     * @param patientcode8
     * @return
     */
    List<String> createQrCodeBase64(String patientcode, String patientcode8, YiyingConfig yiyingConfig);
}
