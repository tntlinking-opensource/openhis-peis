package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmOrderConflict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 撞单记录(CrmOrderConflict)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
public interface CrmOrderConflictService extends IService<CrmOrderConflict> {

    /**
     * 分页查询[撞单记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmOrderConflict> getPage(PageParam<CrmOrderConflict> page, CrmOrderConflict param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmOrderConflict getInfoById(String id);

}

