package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdInspectCharge;

/**
 * JC检查项目收费项目关联表(MdInspectCharge)服务接口
 *
 * @author ay
 * @since 2024-07-13 13:47:00
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

