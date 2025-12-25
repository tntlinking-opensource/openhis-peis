package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC支付方式表(Dictpayway)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
public interface DictpaywayMapper extends BaseMapper<Dictpayway> {

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param Dictpayway查询参数
     * @return 分页数据
     */
    IPage<Dictpayway> getList(PageParam<Dictpayway> page, @Param("param") Dictpayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Dictpayway getInfoById(@Param("id") String id);

}
