package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdElectroAudiometer;
import org.apache.ibatis.annotations.Param;

/**
 * KS电测听(MdElectroAudiometer)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 16:03:15
 */
public interface MdElectroAudiometerMapper extends BaseMapper<MdElectroAudiometer> {

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param MdElectroAudiometer查询参数
     * @return 分页数据
     */
    IPage<MdElectroAudiometer> getPage(PageParam<MdElectroAudiometer> page, @Param("param") MdElectroAudiometer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdElectroAudiometer getInfoById(@Param("id") String id);

}
