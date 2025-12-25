package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PeisReserPayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者结算方式表(PeisReserPayway)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:01
 */
public interface PeisReserPaywayMapper extends BaseMapper<PeisReserPayway> {

    /**
     * 分页查询[体检者结算方式表]列表
     *
     * @param page  分页参数
     * @param param PeisReserPayway查询参数
     * @return 分页数据
     */
    IPage<PeisReserPayway> getPage(PageParam<PeisReserPayway> page, @Param("param") PeisReserPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisReserPayway getInfoById(@Param("id") String id);

}
