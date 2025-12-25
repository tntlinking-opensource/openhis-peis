package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.model.BsSettlement;
import com.center.medical.app.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 业务结算表(BsSettlement)数据库访问层
 *
 * @author ay
 * @since 2024-06-17 16:01:58
 */
public interface BsSettlementMapper extends BaseMapper<BsSettlement> {

    /**
     * 分页查询[业务结算表]列表
     *
     * @param page  分页参数
     * @param param BsSettlement查询参数
     * @return 分页数据
     */
    IPage<BsSettlement> getPage(PageParam<BsSettlement> page, @Param("param") BsSettlement param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键settlementId
     * @return 详情信息
     */
    BsSettlement getInfoById(@Param("id") String id);

    /**
     * 更新成支付状态
     *
     * @param payNo    支付单号
     * @param bizPayNo 外包支付单号
     * @param version  版本号
     * @return 是否更新成功
     */
    int updateToPay(@Param("payNo") String payNo, @Param("bizPayNo") String bizPayNo, @Param("version") Integer version);
}
