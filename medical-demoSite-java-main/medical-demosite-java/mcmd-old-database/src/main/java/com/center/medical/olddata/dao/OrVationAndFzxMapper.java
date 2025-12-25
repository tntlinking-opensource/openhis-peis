package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrVationAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * 团体任务分中心（不会被同步）(MdVationAndFzx)数据库访问层
 *
 * @author ay
 * @since 2024-04-11 11:00:25
 */
public interface OrVationAndFzxMapper extends BaseMapper<OrVationAndFzx> {

    /**
     * 分页查询[团体任务分中心（不会被同步）]列表
     *
     * @param page  分页参数
     * @param param MdVationAndFzx查询参数
     * @return 分页数据
     */
    IPage<OrVationAndFzx> getPage(PageParam<OrVationAndFzx> page, @Param("param") OrVationAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrVationAndFzx getInfoById(@Param("id") String id);

}
