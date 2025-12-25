package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrElectroAudiometer;
import org.apache.ibatis.annotations.Param;

/**
 * KS电测听(ElectroAudiometer)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 15:45:03
 */
public interface OrElectroAudiometerMapper extends BaseMapper<OrElectroAudiometer> {

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param ElectroAudiometer查询参数
     * @return 分页数据
     */
    IPage<OrElectroAudiometer> getPage(PageParam<OrElectroAudiometer> page, @Param("param") OrElectroAudiometer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrElectroAudiometer getInfoById(@Param("id") String id);

}
