package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Dictpayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC支付方式表(Dictpayway)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:14
 */
public interface DictpaywayMapper extends BaseMapper<Dictpayway> {

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param Dictpayway查询参数
     * @return 分页数据
     */
    IPage<Dictpayway> getPage(PageParam<Dictpayway> page, @Param("param") Dictpayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Dictpayway getInfoById(@Param("id") String id);

}
