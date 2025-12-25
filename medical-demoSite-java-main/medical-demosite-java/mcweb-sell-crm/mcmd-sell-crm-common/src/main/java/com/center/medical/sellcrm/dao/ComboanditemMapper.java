package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboanditem;
import com.center.medical.sellcrm.bean.vo.ComboAndItemVo;
import com.center.medical.sellcrm.bean.vo.GetItemsVo;
import com.center.medical.sellcrm.bean.vo.ItemDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:29
 */
public interface ComboanditemMapper extends BaseMapper<Comboanditem> {

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Comboanditem查询参数
     * @return 分页数据
     */
    IPage<Comboanditem> getPageList(PageParam<Comboanditem> page, @Param("param") Comboanditem param);

    /**
     * 获取收费项目列表
     *
     * @param page    分页参数
     * @param comboId 套餐id
     * @return 列表数据
     */
    IPage<ComboAndItemVo> getListByComboId(PageParam<Comboanditem> page, @Param("comboId") String comboId);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Comboanditem getInfoById(@Param("id") String id);

    /**
     * 查询最小套餐关联的收费项目
     *
     * @param page
     * @param tcId
     * @return
     */
    IPage<ItemDataVo> getItemData(PageParam<ItemDataVo> page, @Param("tcId") String tcId);

    /**
     * 获取收费项目
     *
     * @param tcId
     * @return
     */
    List<GetItemsVo> getItems(@Param("tcId") String tcId);
}
