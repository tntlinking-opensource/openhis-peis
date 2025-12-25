package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientChargeMain;

/**
 * (PeispatientChargeMain)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:55:02
 */
public interface OrPeispatientChargeMainService extends IService<OrPeispatientChargeMain> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientChargeMain> getPage(PageParam<OrPeispatientChargeMain> page, OrPeispatientChargeMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientChargeMain getInfoById(String id);

    /**
     * 通过体检号获取记录
     *
     * @param patientcode
     * @return
     */
    OrPeispatientChargeMain getByPatientCode(String patientcode);
}

