package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdRemittance;
import org.apache.ibatis.annotations.Param;

/**
 * kingdeeremittance(KdRemittance)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
public interface KdRemittanceMapper extends BaseMapper<KdRemittance> {

    /**
     * 分页查询[kingdeeremittance]列表
     *
     * @param page  分页参数
     * @param param KdRemittance查询参数
     * @return 分页数据
     */
    IPage<KdRemittance> getPage(PageParam<KdRemittance> page, @Param("param") KdRemittance param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fid
     * @return 详情信息
     */
    KdRemittance getInfoById(@Param("id") String id);

}
