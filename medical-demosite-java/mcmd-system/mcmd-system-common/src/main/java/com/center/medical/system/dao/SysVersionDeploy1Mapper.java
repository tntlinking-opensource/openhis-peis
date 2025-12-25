package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionDeploy1;
import org.apache.ibatis.annotations.Param;

/**
 * 版本控制-更新记录(SysVersionDeploy1)数据库访问层
 *
 * @author makejava
 * @since 2024-01-23 10:36:17
 */
public interface SysVersionDeploy1Mapper extends BaseMapper<SysVersionDeploy1> {

    /**
     * 分页查询[版本控制-更新记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionDeploy查询参数
     * @return 分页数据
     */
    IPage<SysVersionDeploy1> getPage(PageParam<SysVersionDeploy1> page, @Param("param") SysVersionDeploy1 param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionDeploy1 getInfoById(@Param("id") String id);

}
