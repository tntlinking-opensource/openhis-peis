package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 微信小程序操作日志(AppLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:44
 */
public interface AppLogMapper extends BaseMapper<AppLog> {

    /**
     * 分页查询[微信小程序操作日志]列表
     *
     * @param page  分页参数
     * @param param AppLog查询参数
     * @return 分页数据
     */
    IPage<AppLog> getPage(PageParam<AppLog> page, @Param("param") AppLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppLog getInfoById(@Param("id") String id);

}
