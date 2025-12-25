package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsInspectCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-项目检查费用(MdPacsInspectCharge)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
public interface MdPacsInspectChargeService extends IService<MdPacsInspectCharge> {

    /**
     * 分页查询[PACS-项目检查费用]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsInspectCharge> getPage(PageParam<MdPacsInspectCharge> page, MdPacsInspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsInspectCharge getInfoById(String id);

}

