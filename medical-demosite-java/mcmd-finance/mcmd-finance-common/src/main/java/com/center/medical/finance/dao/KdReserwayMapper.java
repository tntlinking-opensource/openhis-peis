package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdReserway;
import org.apache.ibatis.annotations.Param;

/**
 * kingdeereserway(KdReserway)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
 */
public interface KdReserwayMapper extends BaseMapper<KdReserway> {

    /**
     * 分页查询[kingdeereserway]列表
     *
     * @param page  分页参数
     * @param param KdReserway查询参数
     * @return 分页数据
     */
    IPage<KdReserway> getPage(PageParam<KdReserway> page, @Param("param") KdReserway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    KdReserway getInfoById(@Param("id") String id);

}
