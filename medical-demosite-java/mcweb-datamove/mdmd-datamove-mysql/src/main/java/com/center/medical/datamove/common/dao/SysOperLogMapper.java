package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysOperLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志记录(SysOperLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    /**
     * 分页查询[操作日志记录]列表
     *
     * @param page  分页参数
     * @param param SysOperLog查询参数
     * @return 分页数据
     */
    IPage<SysOperLog> getPage(PageParam<SysOperLog> page, @Param("param") SysOperLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键operId
     * @return 详情信息
     */
    SysOperLog getInfoById(@Param("id") Long id);

}
