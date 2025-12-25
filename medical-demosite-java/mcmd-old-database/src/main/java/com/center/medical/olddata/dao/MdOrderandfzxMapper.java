package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdOrderandfzx;
import org.apache.ibatis.annotations.Param;

/**
 * 订单与分中心关联表(MdOrderandfzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:09:35
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
