package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionNotity;
import org.apache.ibatis.annotations.Param;

/**
 * 版本控制-新版本通知记录(SysVersionNotity)数据库访问层
 *
 * @author makejava
 * @since 2024-04-26 10:52:08
 */
public interface SysVersionNotityMapper extends BaseMapper<SysVersionNotity> {

    /**
     * 分页查询[版本控制-新版本通知记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionNotity查询参数
     * @return 分页数据
     */
    IPage<SysVersionNotity> getPage(PageParam<SysVersionNotity> page, @Param("param") SysVersionNotity param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionNotity getInfoById(@Param("id") Integer id);

}
