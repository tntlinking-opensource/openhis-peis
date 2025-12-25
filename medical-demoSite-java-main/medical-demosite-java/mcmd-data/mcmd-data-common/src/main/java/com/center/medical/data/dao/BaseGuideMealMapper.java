package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseGuideMeal;
import org.apache.ibatis.annotations.Param;

/**
 * 基础推荐套餐(BaseGuideMeal)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:14
 */
public interface BaseGuideMealMapper extends BaseMapper<BaseGuideMeal> {

    /**
     * 分页查询[基础推荐套餐]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMeal查询参数
     * @return 分页数据
     */
    IPage<BaseGuideMeal> getList(PageParam<BaseGuideMeal> page, @Param("param") BaseGuideMeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseGuideMeal getInfoById(@Param("id") String id);

}
