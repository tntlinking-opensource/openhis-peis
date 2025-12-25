package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationDrug;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病/禁忌症(OccupationDrug)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:18
 */
public interface OccupationDrugMapper extends BaseMapper<OccupationDrug> {

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param OccupationDrug查询参数
     * @return 分页数据
     */
    IPage<OccupationDrug> getList(PageParam<OccupationDrug> page, @Param("param") OccupationDrug param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationDrug getInfoById(@Param("id") String id);

}
