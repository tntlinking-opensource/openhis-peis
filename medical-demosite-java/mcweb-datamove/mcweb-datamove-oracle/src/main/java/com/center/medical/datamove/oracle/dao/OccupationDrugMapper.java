package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationDrug;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病/禁忌症(OccupationDrug)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:19
 */
public interface OccupationDrugMapper extends BaseMapper<OccupationDrug> {

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param OccupationDrug查询参数
     * @return 分页数据
     */
    IPage<OccupationDrug> getPage(PageParam<OccupationDrug> page, @Param("param") OccupationDrug param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OccupationDrug getInfoById(@Param("id") String id);

}
