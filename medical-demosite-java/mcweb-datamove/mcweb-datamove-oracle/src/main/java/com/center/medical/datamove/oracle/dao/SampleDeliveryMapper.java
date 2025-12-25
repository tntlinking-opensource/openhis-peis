package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SampleDelivery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS样本录入(SampleDelivery)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:33
 */
public interface SampleDeliveryMapper extends BaseMapper<SampleDelivery> {

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param SampleDelivery查询参数
     * @return 分页数据
     */
    IPage<SampleDelivery> getPage(PageParam<SampleDelivery> page, @Param("param") SampleDelivery param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SampleDelivery getInfoById(@Param("id") String id);

}
