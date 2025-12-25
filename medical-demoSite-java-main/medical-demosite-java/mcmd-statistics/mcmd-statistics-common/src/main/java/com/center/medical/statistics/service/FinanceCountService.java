package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.FinanceCountParam;
import com.center.medical.statistics.bean.vo.FCTotalVo;
import com.center.medical.statistics.bean.vo.FinanceAmountVo;
import com.center.medical.statistics.bean.vo.FinanceCountVo;

import java.util.List;

/**
 * 体检费用统计-收费明细(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface FinanceCountService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FinanceCountVo> getList(PageParam<FinanceCountVo> page, FinanceCountParam param);


    /**
     * 导出财务收费统计
     * @param param
     * @return
     */
    List<FinanceCountVo> exportData(FinanceCountParam param);

    /**
     * 分页查询费用合计
     * @param page
     * @param param
     * @return
     */
    IPage<FCTotalVo> getTotalList(PageParam<FCTotalVo> page, FinanceCountParam param);

    /**
     * 导出财务收费汇总
     * @param param
     * @return
     */
    List<FCTotalVo> totalExport(FinanceCountParam param);

    /**
     * 获取合计数据
     * @param param
     * @return
     */
    FinanceAmountVo financeCountAmount(FinanceCountParam param);
}

