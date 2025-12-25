package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.CreateOrderQtxz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 线上变更订单前台须知记录(CreateOrderQtxz)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:53
 */
public interface CreateOrderQtxzMapper extends BaseMapper<CreateOrderQtxz> {

    /**
     * 分页查询[线上变更订单前台须知记录]列表
     *
     * @param page  分页参数
     * @param param CreateOrderQtxz查询参数
     * @return 分页数据
     */
    IPage<CreateOrderQtxz> getPage(PageParam<CreateOrderQtxz> page, @Param("param") CreateOrderQtxz param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreateOrderQtxz getInfoById(@Param("id") String id);

}
