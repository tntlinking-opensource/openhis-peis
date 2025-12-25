package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Upperower;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 上下级关系管理表(Upperower)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:52
 */
public interface UpperowerMapper extends BaseMapper<Upperower> {

    /**
     * 分页查询[上下级关系管理表]列表
     *
     * @param page  分页参数
     * @param param Upperower查询参数
     * @return 分页数据
     */
    IPage<Upperower> getList(PageParam<Upperower> page, @Param("param") Upperower param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Upperower getInfoById(@Param("id") String id);

}
