package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Comboanditem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:41
 */
public interface ComboanditemService extends IService<Comboanditem> {

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Comboanditem> getPage(PageParam<Comboanditem> page, Comboanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Comboanditem getInfoById(String id);

}

