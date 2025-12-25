package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrComboanditem;

import java.util.List;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:07:22
 */
public interface OrComboanditemService extends IService<OrComboanditem> {

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrComboanditem> getPage(PageParam<OrComboanditem> page, OrComboanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrComboanditem getInfoById(String id);

    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    List<OrComboanditem> getByTcid(String tcid);
}

