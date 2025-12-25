package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOccupationDrug;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病/禁忌症(MdOccupationDrug)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
public interface MdOccupationDrugMapper extends BaseMapper<MdOccupationDrug> {

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param MdOccupationDrug查询参数
     * @return 分页数据
     */
    IPage<MdOccupationDrug> getPage(PageParam<MdOccupationDrug> page, @Param("param") MdOccupationDrug param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOccupationDrug getInfoById(@Param("id") String id);

}
