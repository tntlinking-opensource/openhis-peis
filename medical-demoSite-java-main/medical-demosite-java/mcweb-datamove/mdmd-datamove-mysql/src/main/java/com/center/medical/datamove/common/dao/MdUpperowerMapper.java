package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUpperower;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 上下级关系管理表(MdUpperower)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:14
 */
public interface MdUpperowerMapper extends BaseMapper<MdUpperower> {

    /**
     * 分页查询[上下级关系管理表]列表
     *
     * @param page  分页参数
     * @param param MdUpperower查询参数
     * @return 分页数据
     */
    IPage<MdUpperower> getPage(PageParam<MdUpperower> page, @Param("param") MdUpperower param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUpperower getInfoById(@Param("id") String id);

}
