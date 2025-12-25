package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDictpayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC支付方式表(MdDictpayway)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
public interface MdDictpaywayMapper extends BaseMapper<MdDictpayway> {

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param MdDictpayway查询参数
     * @return 分页数据
     */
    IPage<MdDictpayway> getPage(PageParam<MdDictpayway> page, @Param("param") MdDictpayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDictpayway getInfoById(@Param("id") String id);

}
