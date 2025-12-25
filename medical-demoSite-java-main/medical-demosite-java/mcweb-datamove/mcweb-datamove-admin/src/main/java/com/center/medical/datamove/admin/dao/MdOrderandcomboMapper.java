package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdOrderandcombo;
import org.apache.ibatis.annotations.Param;

/**
 * 订单与套餐关联表(MdOrderandcombo)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:37:59
 */
public interface MdOrderandcomboMapper extends BaseMapper<MdOrderandcombo> {

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param MdOrderandcombo查询参数
     * @return 分页数据
     */
    IPage<MdOrderandcombo> getPage(PageParam<MdOrderandcombo> page, @Param("param") MdOrderandcombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOrderandcombo getInfoById(@Param("id") String id);

}
