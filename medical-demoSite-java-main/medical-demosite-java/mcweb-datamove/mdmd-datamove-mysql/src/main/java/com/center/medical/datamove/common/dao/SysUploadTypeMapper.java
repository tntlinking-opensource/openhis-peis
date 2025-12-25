package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysUploadType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 数据上传接收日志数据类型(SysUploadType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:39
 */
public interface SysUploadTypeMapper extends BaseMapper<SysUploadType> {

    /**
     * 分页查询[数据上传接收日志数据类型]列表
     *
     * @param page  分页参数
     * @param param SysUploadType查询参数
     * @return 分页数据
     */
    IPage<SysUploadType> getPage(PageParam<SysUploadType> page, @Param("param") SysUploadType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUploadType getInfoById(@Param("id") String id);

}
