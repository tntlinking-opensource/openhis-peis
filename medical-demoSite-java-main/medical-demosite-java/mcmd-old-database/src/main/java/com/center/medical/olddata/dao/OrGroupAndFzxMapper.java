package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrGroupAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * (GroupAndFzx)数据库访问层
 *
 * @author ay
 * @since 2024-04-11 10:44:30
 */
public interface OrGroupAndFzxMapper extends BaseMapper<OrGroupAndFzx> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param GroupAndFzx查询参数
     * @return 分页数据
     */
    IPage<OrGroupAndFzx> getPage(PageParam<OrGroupAndFzx> page, @Param("param") OrGroupAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrGroupAndFzx getInfoById(@Param("id") String id);

}
