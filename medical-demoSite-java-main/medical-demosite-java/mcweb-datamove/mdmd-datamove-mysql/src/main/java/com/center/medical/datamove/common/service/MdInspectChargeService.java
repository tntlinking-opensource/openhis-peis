package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdInspectCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC检查项目收费项目关联表(MdInspectCharge)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
public interface MdInspectChargeService extends IService<MdInspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdInspectCharge> getPage(PageParam<MdInspectCharge> page, MdInspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdInspectCharge getInfoById(String id);

}

