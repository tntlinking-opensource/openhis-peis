package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionService;
import com.center.medical.system.bean.param.SysVersionServiceParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 版本控制-系统服务更新包记录(SysVersionService)数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 18:02:37
 */
public interface SysVersionServiceMapper extends BaseMapper<SysVersionService> {

    /**
     * 分页查询[版本控制-系统服务更新包记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionService查询参数
     * @return 分页数据
     */
    IPage<SysVersionService> getPage(PageParam<SysVersionService> page, @Param("param") SysVersionService param);

    /**
     * 查询版本控制-系统服务更新包记录列表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    List<SysVersionService> getList(@Param("param") SysVersionServiceParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionService getInfoById(@Param("id") Integer id);

}
