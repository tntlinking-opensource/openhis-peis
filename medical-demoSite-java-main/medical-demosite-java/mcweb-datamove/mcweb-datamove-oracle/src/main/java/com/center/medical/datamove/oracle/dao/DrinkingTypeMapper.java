package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.DrinkingType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (DrinkingType)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:21
 */
public interface DrinkingTypeMapper extends BaseMapper<DrinkingType> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DrinkingType查询参数
     * @return 分页数据
     */
    IPage<DrinkingType> getPage(PageParam<DrinkingType> page, @Param("param") DrinkingType param);


}
