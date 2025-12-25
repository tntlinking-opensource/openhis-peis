package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmSellOutside;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 外出沟通(CrmSellOutside)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
public interface CrmSellOutsideMapper extends BaseMapper<CrmSellOutside> {

    /**
     * 分页查询[外出沟通]列表
     *
     * @param page  分页参数
     * @param param CrmSellOutside查询参数
     * @return 分页数据
     */
    IPage<CrmSellOutside> getPage(PageParam<CrmSellOutside> page, @Param("param") CrmSellOutside param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellOutside getInfoById(@Param("id") String id);

}
