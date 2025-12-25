package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.SelectOtherPayableDto;
import com.center.medical.finance.bean.model.KdReser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每笔银行汇款结算详情(KdReser)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
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

    /**
     *
     * @param idRemitter
     * @return
     */
    List<SelectOtherPayableDto> selectOtherPayable(@Param("idRemitter") String idRemitter);
}
