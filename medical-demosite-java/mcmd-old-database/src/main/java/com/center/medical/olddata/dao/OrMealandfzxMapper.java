package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrMealandfzx;
import org.apache.ibatis.annotations.Param;

/**
 * 普通套餐与分中心关联表(Mealandfzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 22:25:30
 */
public interface OrMealandfzxMapper extends BaseMapper<OrMealandfzx> {

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Mealandfzx查询参数
     * @return 分页数据
     */
    IPage<OrMealandfzx> getPage(PageParam<OrMealandfzx> page, @Param("param") OrMealandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrMealandfzx getInfoById(@Param("id") String id);

}
