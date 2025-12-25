package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Chest;
import com.center.medical.bean.param.ChestParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单柜子信息(Chest)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-06 08:59:19
 */
public interface ChestMapper extends BaseMapper<Chest> {

    /**
     * 分页查询[订单柜子信息]列表
     *
     * @param page  分页参数
     * @param param Chest查询参数
     * @return 分页数据
     */
    IPage<Chest> getPage(PageParam<Chest> page, @Param("param") ChestParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Chest getInfoById(@Param("id") String id);

    /**
     * 获取订单柜子信息导出数据
     * @param param
     * @return
     */
    List<Chest> getExportData(@Param("param")Chest param);
}
