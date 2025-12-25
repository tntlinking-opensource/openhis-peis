package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Mealanditem;
import com.center.medical.sellcrm.bean.param.ApproveTjtcDataParam;
import com.center.medical.sellcrm.bean.vo.ItemDataVo;
import com.center.medical.sellcrm.bean.vo.getTjtcAndItemVo;

import java.util.List;
import java.util.Map;

/**
 * 普通套餐与收费项目关联表(Mealanditem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-21 19:41:49
 */
public interface MealanditemService extends IService<Mealanditem> {

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Mealanditem> getPage(PageParam<Mealanditem> page, Mealanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Mealanditem getInfoById(String id);

    /**
     * 获取收费项目
     * @param page
     * @param tcId
     * @param tcstate
     * @return
     */
    IPage<ItemDataVo> getItemData(PageParam<ItemDataVo> page, String tcId, String tcstate);

    /**
     * 获取套餐下关联的收费项目
     * @param approveTjtcDataParam
     * @return
     */
    List<getTjtcAndItemVo> getTjtcAndItemData(ApproveTjtcDataParam approveTjtcDataParam);

    /**
     * 编辑时收费项目右边显示
     * @param tcId
     * @return
     */
    List<Map> getSfxmsData(String tcId);
}

