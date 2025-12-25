package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.KdReser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 每笔银行汇款结算详情(KdReser)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
public interface KdReserMapper extends BaseMapper<KdReser> {

    /**
     * 分页查询[每笔银行汇款结算详情]列表
     *
     * @param page  分页参数
     * @param param KdReser查询参数
     * @return 分页数据
     */
    IPage<KdReser> getPage(PageParam<KdReser> page, @Param("param") KdReser param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    KdReser getInfoById(@Param("id") String id);

}
