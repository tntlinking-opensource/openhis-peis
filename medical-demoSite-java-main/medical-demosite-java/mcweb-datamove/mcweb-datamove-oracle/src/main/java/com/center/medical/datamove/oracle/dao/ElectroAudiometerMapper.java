package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ElectroAudiometer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS电测听(ElectroAudiometer)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:34
 */
public interface ElectroAudiometerMapper extends BaseMapper<ElectroAudiometer> {

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param ElectroAudiometer查询参数
     * @return 分页数据
     */
    IPage<ElectroAudiometer> getPage(PageParam<ElectroAudiometer> page, @Param("param") ElectroAudiometer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ElectroAudiometer getInfoById(@Param("id") String id);

}
