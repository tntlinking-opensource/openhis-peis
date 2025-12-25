package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsInspectCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-项目检查费用(PacsInspectCharge)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:50
 */
public interface PacsInspectChargeService extends IService<PacsInspectCharge> {

    /**
     * 分页查询[PACS-项目检查费用]列表
     *
     * @param page  分页参数
     * @param param PacsInspectCharge查询参数
     * @return 分页数据
     */
    IPage<PacsInspectCharge> getList(PageParam<PacsInspectCharge> page, PacsInspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsInspectCharge getInfoById(String id);

}

