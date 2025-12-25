package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SampleConnect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS样本交接(SampleConnect)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:31
 */
public interface SampleConnectMapper extends BaseMapper<SampleConnect> {

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param SampleConnect查询参数
     * @return 分页数据
     */
    IPage<SampleConnect> getPage(PageParam<SampleConnect> page, @Param("param") SampleConnect param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SampleConnect getInfoById(@Param("id") String id);

}
