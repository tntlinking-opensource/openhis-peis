package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboanditem;
import com.center.medical.sellcrm.bean.vo.ComboAndItemVo;
import com.center.medical.sellcrm.bean.vo.GetItemsVo;

import java.util.List;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:30
 */
public interface ComboanditemService extends IService<Comboanditem> {

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Comboanditem查询参数
     * @return 分页数据
     */
    IPage<Comboanditem> getList(PageParam<Comboanditem> page, Comboanditem param);

    /**
     * 获取收费项目列表
     *
     * @param page    分页参数
     * @param comboId 套餐id
     * @return 分页数据
     */
    IPage<ComboAndItemVo> getListByComboId(PageParam<Comboanditem> page, String comboId);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Comboanditem getInfoById(String id);

    /**
     * 获取收费项目
     *
     * @param tcId
     * @return
     */
    List<GetItemsVo> getItems(String tcId);
}

