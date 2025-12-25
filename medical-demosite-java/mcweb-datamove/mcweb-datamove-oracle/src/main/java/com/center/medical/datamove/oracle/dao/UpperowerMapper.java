package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Upperower;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 上下级关系管理表(Upperower)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:41
 */
public interface UpperowerMapper extends BaseMapper<Upperower> {

    /**
     * 分页查询[上下级关系管理表]列表
     *
     * @param page  分页参数
     * @param param Upperower查询参数
     * @return 分页数据
     */
    IPage<Upperower> getPage(PageParam<Upperower> page, @Param("param") Upperower param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Upperower getInfoById(@Param("id") String id);

}
