package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Nation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC民族(Nation)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
public interface NationMapper extends BaseMapper<Nation> {

    /**
     * 分页查询[JC民族]列表
     *
     * @param page  分页参数
     * @param param Nation查询参数
     * @return 分页数据
     */
    IPage<Nation> getList(PageParam<Nation> page, @Param("param") Nation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Nation getInfoById(@Param("id") String id);

}
