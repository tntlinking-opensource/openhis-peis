package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.CreateOrderQtxz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 线上变更订单前台须知记录(CreateOrderQtxz)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
public interface CreateOrderQtxzMapper extends BaseMapper<CreateOrderQtxz> {

    /**
     * 分页查询[线上变更订单前台须知记录]列表
     *
     * @param page  分页参数
     * @param param CreateOrderQtxz查询参数
     * @return 分页数据
     */
    IPage<CreateOrderQtxz> getList(PageParam<CreateOrderQtxz> page, @Param("param") CreateOrderQtxz param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CreateOrderQtxz getInfoById(@Param("id") String id);

}
