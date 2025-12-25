package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.PeispatientChargeMain;

/**
 * (PeispatientChargeMain)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:55:02
 */
public interface OrPeispatientChargeMainService extends IService<PeispatientChargeMain> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientChargeMain> getPage(PageParam<PeispatientChargeMain> page, PeispatientChargeMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeispatientChargeMain getInfoById(String id);

    /**
     * 通过体检号获取记录
     *
     * @param patientcode
     * @return
     */
    PeispatientChargeMain getByPatientCode(String patientcode);
}

