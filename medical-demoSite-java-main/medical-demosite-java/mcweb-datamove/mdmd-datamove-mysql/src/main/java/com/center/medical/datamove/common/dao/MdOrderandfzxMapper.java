package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOrderandfzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单与分中心关联表(MdOrderandfzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:29
 */
public interface MdOrderandfzxMapper extends BaseMapper<MdOrderandfzx> {

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdOrderandfzx查询参数
     * @return 分页数据
     */
    IPage<MdOrderandfzx> getPage(PageParam<MdOrderandfzx> page, @Param("param") MdOrderandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOrderandfzx getInfoById(@Param("id") String id);

}
