package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.VationAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 团体任务分中心（不会被同步）(VationAndFzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:46
 */
public interface VationAndFzxMapper extends BaseMapper<VationAndFzx> {

    /**
     * 分页查询[团体任务分中心（不会被同步）]列表
     *
     * @param page  分页参数
     * @param param VationAndFzx查询参数
     * @return 分页数据
     */
    IPage<VationAndFzx> getList(PageParam<VationAndFzx> page, @Param("param") VationAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    VationAndFzx getInfoById(@Param("id") String id);

}
