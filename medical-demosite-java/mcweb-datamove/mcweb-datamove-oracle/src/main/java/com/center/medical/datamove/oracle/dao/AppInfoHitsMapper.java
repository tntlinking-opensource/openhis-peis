package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppInfoHits;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序资讯点击记录(AppInfoHits)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:38
 */
public interface AppInfoHitsMapper extends BaseMapper<AppInfoHits> {

    /**
     * 分页查询[小程序资讯点击记录]列表
     *
     * @param page  分页参数
     * @param param AppInfoHits查询参数
     * @return 分页数据
     */
    IPage<AppInfoHits> getPage(PageParam<AppInfoHits> page, @Param("param") AppInfoHits param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppInfoHits getInfoById(@Param("id") String id);

}
