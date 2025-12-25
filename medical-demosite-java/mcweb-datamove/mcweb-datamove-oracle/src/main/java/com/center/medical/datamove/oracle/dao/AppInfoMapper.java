package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * APP???(AppInfo)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:36
 */
public interface AppInfoMapper extends BaseMapper<AppInfo> {

    /**
     * 分页查询[APP???]列表
     *
     * @param page  分页参数
     * @param param AppInfo查询参数
     * @return 分页数据
     */
    IPage<AppInfo> getPage(PageParam<AppInfo> page, @Param("param") AppInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppInfo getInfoById(@Param("id") String id);

}
