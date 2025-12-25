package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.InspectCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC检查项目收费项目关联表(InspectCharge)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:15
 */
public interface InspectChargeService extends IService<InspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<InspectCharge> getPage(PageParam<InspectCharge> page, InspectCharge param);


}

