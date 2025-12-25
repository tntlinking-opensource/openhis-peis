package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrComboandfzx;
import org.apache.ibatis.annotations.Param;

/**
 * 最小套餐与分中心关联表(Comboandfzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 22:07:19
 */
public interface OrComboandfzxMapper extends BaseMapper<OrComboandfzx> {

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Comboandfzx查询参数
     * @return 分页数据
     */
    IPage<OrComboandfzx> getPage(PageParam<OrComboandfzx> page, @Param("param") OrComboandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrComboandfzx getInfoById(@Param("id") String id);

}
