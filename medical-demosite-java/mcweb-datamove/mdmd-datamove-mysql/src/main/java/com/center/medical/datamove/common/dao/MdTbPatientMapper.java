package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTbPatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 同步主表(MdTbPatient)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:53
 */
public interface MdTbPatientMapper extends BaseMapper<MdTbPatient> {

    /**
     * 分页查询[同步主表]列表
     *
     * @param page  分页参数
     * @param param MdTbPatient查询参数
     * @return 分页数据
     */
    IPage<MdTbPatient> getPage(PageParam<MdTbPatient> page, @Param("param") MdTbPatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTbPatient getInfoById(@Param("id") String id);

}
