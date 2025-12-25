package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionDeploy;
import org.apache.ibatis.annotations.Param;

/**
 * 版本控制-分中心版本更新记录(SysVersionDeploy)数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 18:02:36
 */
public interface SysVersionDeployMapper extends BaseMapper<SysVersionDeploy> {

    /**
     * 分页查询[版本控制-分中心版本更新记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionDeploy查询参数
     * @return 分页数据
     */
    IPage<SysVersionDeploy> getPage(PageParam<SysVersionDeploy> page, @Param("param") SysVersionDeploy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionDeploy getInfoById(@Param("id") String id);

}
