package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Mealanditem;
import com.center.medical.sellcrm.bean.vo.ItemDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 普通套餐与收费项目关联表(Mealanditem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-21 19:41:49
 */
public interface MealanditemMapper extends BaseMapper<Mealanditem> {

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Mealanditem查询参数
     * @return 分页数据
     */
    IPage<Mealanditem> getPage(PageParam<Mealanditem> page, @Param("param") Mealanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Mealanditem getInfoById(@Param("id") String id);

    /**
     * 获取收费项目
     *
     * @param page
     * @param tcId
     * @return
     */
    IPage<ItemDataVo> getItemData(PageParam<ItemDataVo> page, @Param("tcId") String tcId);

    /**
     * 通过套餐id获取关联表
     * @param tcId
     * @return
     */
    List<Mealanditem> getMAIByTcid(@Param("tcId")String tcId);
}
