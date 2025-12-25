package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SysUploadLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 数据上传接收日志(SysUploadLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:06
 */
public interface SysUploadLogMapper extends BaseMapper<SysUploadLog> {

    /**
     * 分页查询[数据上传接收日志]列表
     *
     * @param page  分页参数
     * @param param SysUploadLog查询参数
     * @return 分页数据
     */
    IPage<SysUploadLog> getPage(PageParam<SysUploadLog> page, @Param("param") SysUploadLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUploadLog getInfoById(@Param("id") String id);

}
