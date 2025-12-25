package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmClientcommon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户公共池(CrmClientcommon)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
public interface CrmClientcommonService extends IService<CrmClientcommon> {

    /**
     * 分页查询[客户公共池]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmClientcommon> getPage(PageParam<CrmClientcommon> page, CrmClientcommon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmClientcommon getInfoById(String id);

}

