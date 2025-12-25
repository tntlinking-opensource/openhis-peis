package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Nation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC民族(Nation)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:08
 */
public interface NationMapper extends BaseMapper<Nation> {

    /**
     * 分页查询[JC民族]列表
     *
     * @param page  分页参数
     * @param param Nation查询参数
     * @return 分页数据
     */
    IPage<Nation> getPage(PageParam<Nation> page, @Param("param") Nation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Nation getInfoById(@Param("id") String id);

}
