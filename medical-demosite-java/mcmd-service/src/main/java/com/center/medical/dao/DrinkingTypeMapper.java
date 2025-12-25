package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.DrinkingType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 饮酒种类(DrinkingType)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
 */
public interface DrinkingTypeMapper extends BaseMapper<DrinkingType> {

    /**
     * 分页查询[饮酒种类]列表
     *
     * @param page  分页参数
     * @param param DrinkingType查询参数
     * @return 分页数据
     */
    IPage<DrinkingType> getList(PageParam<DrinkingType> page, @Param("param") DrinkingType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrinkingType getInfoById(@Param("id") String id);

}
