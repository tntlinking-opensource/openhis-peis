package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.OrderConflict;
import com.center.medical.sellcrm.bean.param.OrderConflictDeallParam;
import com.center.medical.sellcrm.bean.param.OrderConflictParam;
import com.center.medical.sellcrm.bean.vo.OrderConflictVo;

/**
 * 撞单记录(OrderConflict)表服务接口
 *
 * @author 路飞船长
 * @since 2023-04-27 18:17:31
 */
public interface OrderConflictService extends IService<OrderConflict> {

    /**
     * 分页查询[撞单记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrderConflictVo> getPage(PageParam<OrderConflictVo> page, OrderConflictParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrderConflictVo getInfoById(String id);

    /**
     * 撞单处理
     *
     * @param param 处理参数
     * @return 处理结果结果
     */
    Boolean deal(OrderConflictDeallParam param);
}

