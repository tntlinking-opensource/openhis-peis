package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSampleDelivery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS样本录入(MdSampleDelivery)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:24
 */
public interface MdSampleDeliveryMapper extends BaseMapper<MdSampleDelivery> {

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param MdSampleDelivery查询参数
     * @return 分页数据
     */
    IPage<MdSampleDelivery> getPage(PageParam<MdSampleDelivery> page, @Param("param") MdSampleDelivery param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSampleDelivery getInfoById(@Param("id") String id);

}
