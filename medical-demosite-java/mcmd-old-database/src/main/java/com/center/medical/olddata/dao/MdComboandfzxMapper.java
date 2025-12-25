package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdComboandfzx;
import org.apache.ibatis.annotations.Param;

/**
 * 最小套餐与分中心关联表(MdComboandfzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 22:05:49
 */
public interface MdComboandfzxMapper extends BaseMapper<MdComboandfzx> {

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdComboandfzx查询参数
     * @return 分页数据
     */
    IPage<MdComboandfzx> getPage(PageParam<MdComboandfzx> page, @Param("param") MdComboandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdComboandfzx getInfoById(@Param("id") String id);

}
