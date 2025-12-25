package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.IRPageParam;
import com.center.medical.finance.bean.vo.IRPageVo;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发票管理(Peisorgreservationreceipt)表数据库访问层
 *
 * @author ay
 * @since 2023-04-04 11:00:04
 */
public interface InvoiceRequestMapper extends BaseMapper<Peisorgreservationreceipt> {

    /**
     * 分页查询[发票]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationreceipt查询参数
     * @return 分页数据
     */
    IPage<IRPageVo> getList(PageParam<IRPageVo> page, @Param("param") IRPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservationreceipt getInfoById(@Param("id") String id);

    /**
     * 发票导出
     *
     * @param param
     * @return
     */
    List<IRPageVo> getExportData(@Param("param") IRPageParam param);

    /**
     * 获取图表数据
     *
     * @param param
     * @return
     */
    String getBarData(@Param("param") BaseParam param, @Param("status") Integer status);
}
