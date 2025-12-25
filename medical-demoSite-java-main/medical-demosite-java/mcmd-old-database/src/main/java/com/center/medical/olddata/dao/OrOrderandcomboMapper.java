package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrOrderandcombo;
import org.apache.ibatis.annotations.Param;

/**
 * 订单与套餐关联表(Orderandcombo)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:38:46
 */
public interface OrOrderandcomboMapper extends BaseMapper<OrOrderandcombo> {

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandcombo查询参数
     * @return 分页数据
     */
    IPage<OrOrderandcombo> getPage(PageParam<OrOrderandcombo> page, @Param("param") OrOrderandcombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrOrderandcombo getInfoById(@Param("id") String id);

}
