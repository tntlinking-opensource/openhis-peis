package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrugstoreDrug;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 药品基础表(MdDrugstoreDrug)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugstoreDrugMapper extends BaseMapper<MdDrugstoreDrug> {

    /**
     * 分页查询[药品基础表]列表
     *
     * @param page  分页参数
     * @param param MdDrugstoreDrug查询参数
     * @return 分页数据
     */
    IPage<MdDrugstoreDrug> getPage(PageParam<MdDrugstoreDrug> page, @Param("param") MdDrugstoreDrug param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugstoreDrug getInfoById(@Param("id") String id);

}
