package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.TbPatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 同步主表(TbPatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:18
 */
public interface TbPatientMapper extends BaseMapper<TbPatient> {

    /**
     * 分页查询[同步主表]列表
     *
     * @param page  分页参数
     * @param param TbPatient查询参数
     * @return 分页数据
     */
    IPage<TbPatient> getList(PageParam<TbPatient> page, @Param("param") TbPatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TbPatient getInfoById(@Param("id") String id);

}
