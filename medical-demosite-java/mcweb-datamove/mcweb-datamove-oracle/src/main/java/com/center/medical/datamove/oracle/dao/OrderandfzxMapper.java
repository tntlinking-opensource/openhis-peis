package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Orderandfzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单与分中心关联表(Orderandfzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:25
 */
public interface OrderandfzxMapper extends BaseMapper<Orderandfzx> {

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandfzx查询参数
     * @return 分页数据
     */
    IPage<Orderandfzx> getPage(PageParam<Orderandfzx> page, @Param("param") Orderandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Orderandfzx getInfoById(@Param("id") String id);

}
