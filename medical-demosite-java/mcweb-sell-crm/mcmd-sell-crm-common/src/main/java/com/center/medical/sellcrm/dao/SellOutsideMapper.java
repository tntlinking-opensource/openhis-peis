package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.SellOutside;
import org.apache.ibatis.annotations.Param;

/**
 * 外出沟通(SellOutside)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:24
 */
public interface SellOutsideMapper extends BaseMapper<SellOutside> {

    /**
     * 分页查询[外出沟通]列表
     *
     * @param page  分页参数
     * @param param SellOutside查询参数
     * @return 分页数据
     */
    IPage<SellOutside> getPage(PageParam<SellOutside> page, @Param("param") SellOutside param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SellOutside getInfoById(@Param("id") String id);

}
