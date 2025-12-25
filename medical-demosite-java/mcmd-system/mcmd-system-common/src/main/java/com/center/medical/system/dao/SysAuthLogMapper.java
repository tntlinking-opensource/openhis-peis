package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysAuthLog;
import org.apache.ibatis.annotations.Param;

/**
 * 系统授权记录(SysAuthLog)数据库访问层
 *
 * @author makejava
 * @since 2024-01-17 20:20:02
 */
public interface SysAuthLogMapper extends BaseMapper<SysAuthLog> {

    /**
     * 分页查询[系统授权记录]列表
     *
     * @param page  分页参数
     * @param param SysAuthLog查询参数
     * @return 分页数据
     */
    IPage<SysAuthLog> getPage(PageParam<SysAuthLog> page, @Param("param") SysAuthLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysAuthLog getInfoById(@Param("id") Long id);

}
