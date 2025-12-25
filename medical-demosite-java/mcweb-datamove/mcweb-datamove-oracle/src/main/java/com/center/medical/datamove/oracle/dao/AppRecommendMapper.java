package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppRecommend;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (AppRecommend)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:53
 */
public interface AppRecommendMapper extends BaseMapper<AppRecommend> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppRecommend查询参数
     * @return 分页数据
     */
    IPage<AppRecommend> getPage(PageParam<AppRecommend> page, @Param("param") AppRecommend param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppRecommend getInfoById(@Param("id") String id);

}
