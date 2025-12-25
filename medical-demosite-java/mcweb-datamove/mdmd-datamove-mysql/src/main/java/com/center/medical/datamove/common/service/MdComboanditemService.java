package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdComboanditem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 维护最小套餐与收费项目关联表(MdComboanditem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
public interface MdComboanditemService extends IService<MdComboanditem> {

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdComboanditem> getPage(PageParam<MdComboanditem> page, MdComboanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdComboanditem getInfoById(String id);

}

