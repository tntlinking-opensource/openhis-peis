package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.common.utils.page.PageAdapter;
import com.center.medical.sellcrm.bean.model.OrderConflict;
import com.center.medical.sellcrm.bean.param.OrderConflictParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 撞单记录(OrderConflict)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-04-27 18:17:30
 */
public interface OrderConflictMapper extends BaseMapper<OrderConflict> {

    /**
     * 分页查询[撞单记录]列表
     *
     * @param pageAdapter 分页参数
     * @param param       OrderConflict查询参数
     * @return 分页数据
     */
    List<OrderConflict> getPage(@Param("adapter") PageAdapter pageAdapter, @Param("param") OrderConflictParam param);

    /**
     * 获取撞单数量
     *
     * @param param
     * @return
     */
    Long countByParam(@Param("param") OrderConflictParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrderConflict getInfoById(@Param("id") String id);
}
