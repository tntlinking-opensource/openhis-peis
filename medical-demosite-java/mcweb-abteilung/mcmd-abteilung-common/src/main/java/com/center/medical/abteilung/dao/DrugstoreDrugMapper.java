package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.abteilung.bean.model.DrugstoreDrug;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.DrugstoreDrugParam;
import com.center.medical.abteilung.bean.vo.DrugstoreDrugVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品基础表(DrugstoreDrug)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:05
 */
public interface DrugstoreDrugMapper extends BaseMapper<DrugstoreDrug> {

    /**
     * 分页查询[药品基础表]列表
     *
     * @param page  分页参数
     * @param param DrugstoreDrug查询参数
     * @return 分页数据
     */
    IPage<DrugstoreDrugVo> getList(PageParam<DrugstoreDrugVo> page, @Param("param") DrugstoreDrugParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugstoreDrug getInfoById(@Param("id") String id);

    /**
     * 不分页查询
     * @param param
     * @return
     */
    List<DrugstoreDrugVo> getSelectData( @Param("param") DrugstoreDrugParam param);
}
