package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.BsSettlement;
import com.center.medical.appadmin.bean.param.BsSettlementParam;
import com.center.medical.appadmin.bean.vo.BSGetTotalVo;
import com.center.medical.appadmin.bean.vo.BsSettlementVo;
import com.center.medical.common.utils.page.PageParam;
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
    IPage<BsSettlementVo> getPage(PageParam<BsSettlementVo> page, @Param("param") BsSettlementParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键settlementId
     * @return 详情信息
     */
    BsSettlement getInfoById(@Param("id") String id);

    /**
     * 获取总额
     * @param param
     * @return
     */
    BSGetTotalVo getTotal(@Param("param") BsSettlementParam param);
}
