package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Orderandfzx;
import org.apache.ibatis.annotations.Param;

/**
 * 订单与分中心关联表(Orderandfzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:10:27
 */
public interface OrOrderandfzxMapper extends BaseMapper<Orderandfzx> {

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
