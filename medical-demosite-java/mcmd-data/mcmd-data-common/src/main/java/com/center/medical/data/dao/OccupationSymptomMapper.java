package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationSymptom;
import com.center.medical.data.bean.param.ShowDataParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JC职业症状(OccupationSymptom)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:30
 */
public interface OccupationSymptomMapper extends BaseMapper<OccupationSymptom> {

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param OccupationSymptom查询参数
     * @return 分页数据
     */
    IPage<OccupationSymptom> getList(PageParam<OccupationSymptom> page, @Param("param") OccupationSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationSymptom getInfoById(@Param("id") String id);

    /**
     * 根据id获取记录详情，没有被删除的
     *
     * @param id
     * @return
     */
    OccupationSymptom getOccupationSymptomById(@Param("id") String id);
    

    /**
     * 弹窗数据
     *
     * @param param
     * @return
     */
    List<OccupationSymptom> getShowData(@Param("param") ShowDataParam param);
}
