package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.model.SuixingPayConfig;
import com.center.medical.app.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 随行支付配置参数(SuixingPayConfig)数据库访问层
 *
 * @author ay
 * @since 2024-07-12 17:05:40
 */
public interface SuixingPayConfigMapper extends BaseMapper<SuixingPayConfig> {

    /**
     * 分页查询[随行支付配置参数]列表
     *
     * @param page  分页参数
     * @param param SuixingPayConfig查询参数
     * @return 分页数据
     */
    IPage<SuixingPayConfig> getPage(PageParam<SuixingPayConfig> page, @Param("param") SuixingPayConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SuixingPayConfig getInfoById(@Param("id") String id);

    /**
     * 通过分中心id查询
     * @param branchId
     * @return
     */
    SuixingPayConfig getInfoByFzx(@Param("branchId") String branchId);
}
