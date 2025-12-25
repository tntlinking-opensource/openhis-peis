package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmReceiveandsell;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户公共池领取与领取人员关联表(CrmReceiveandsell)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
public interface CrmReceiveandsellService extends IService<CrmReceiveandsell> {

    /**
     * 分页查询[客户公共池领取与领取人员关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmReceiveandsell> getPage(PageParam<CrmReceiveandsell> page, CrmReceiveandsell param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmReceiveandsell getInfoById(String id);

}

