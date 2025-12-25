package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysSqlOptionLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * SQL操作日志表(SysSqlOptionLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
public interface SysSqlOptionLogMapper extends BaseMapper<SysSqlOptionLog> {

    /**
     * 分页查询[SQL操作日志表]列表
     *
     * @param page  分页参数
     * @param param SysSqlOptionLog查询参数
     * @return 分页数据
     */
    IPage<SysSqlOptionLog> getPage(PageParam<SysSqlOptionLog> page, @Param("param") SysSqlOptionLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysSqlOptionLog getInfoById(@Param("id") Long id);

}
