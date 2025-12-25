package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PeisReserPayway;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者结算方式表(PeisReserPayway)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
public interface PeisReserPaywayMapper extends BaseMapper<PeisReserPayway> {

    /**
     * 分页查询[体检者结算方式表]列表
     *
     * @param page  分页参数
     * @param param PeisReserPayway查询参数
     * @return 分页数据
     */
    IPage<PeisReserPayway> getList(PageParam<PeisReserPayway> page, @Param("param") PeisReserPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisReserPayway getInfoById(@Param("id") String id);

    /**
     * 获取结算信息
     *
     * @param id
     * @return
     */
    List<PeisReserPayway> getBillingData(@Param("id") String id);
}
