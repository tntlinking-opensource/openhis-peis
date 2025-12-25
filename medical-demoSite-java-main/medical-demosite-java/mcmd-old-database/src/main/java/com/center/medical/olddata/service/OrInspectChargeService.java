package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrInspectCharge;

import java.util.List;

/**
 * JC检查项目收费项目关联表(InspectCharge)服务接口
 *
 * @author ay
 * @since 2024-07-13 14:27:29
 */
public interface OrInspectChargeService extends IService<OrInspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrInspectCharge> getPage(PageParam<OrInspectCharge> page, OrInspectCharge param);



    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrInspectCharge getInfoById(String id);

    /**
     * 通过收费项目查询
     * @param chargeId
     * @return
     */
    List<OrInspectCharge> getInfoBychargeId(String chargeId);
}

