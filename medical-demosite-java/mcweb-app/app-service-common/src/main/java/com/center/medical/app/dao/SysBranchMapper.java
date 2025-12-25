package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.model.SysBranch;
import com.center.medical.app.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 分中心维护表(SysBranch)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-03-28 18:26:14
 */
public interface SysBranchMapper extends BaseMapper<SysBranch> {

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param SysBranch查询参数
     * @return 分页数据
     */
    IPage<SysBranch> getPage(PageParam<SysBranch> page, @Param("param") SysBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysBranch getInfoById(@Param("id") Integer id);

}
