package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.FinanceCountParam;
import com.center.medical.statistics.bean.vo.FCTotalVo;
import com.center.medical.statistics.bean.vo.FinanceAmountVo;
import com.center.medical.statistics.bean.vo.FinanceCountVo;
import com.center.medical.statistics.dao.FinanceCountMapper;
import com.center.medical.statistics.service.FinanceCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检费用统计-收费明细(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("financeCountService")
@RequiredArgsConstructor
public class FinanceCountServiceImpl extends ServiceImpl<FinanceCountMapper, Peispatient> implements FinanceCountService {

    private final FinanceCountMapper financeCountMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FinanceCountVo> getList(PageParam<FinanceCountVo> page, FinanceCountParam param) {
        return financeCountMapper.getList(page, param);
    }

    /**
     * 导出财务收费统计
     * @param param
     * @return
     */
    @Override
    public List<FinanceCountVo> exportData(FinanceCountParam param) {
        return financeCountMapper.exportData(param);
    }

    /**
     * 分页查询费用合计
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<FCTotalVo> getTotalList(PageParam<FCTotalVo> page, FinanceCountParam param) {
        IPage<FCTotalVo> iPage = financeCountMapper.getTotalList(page, param);
        return iPage;
    }


    /**
     * 导出财务收费汇总
     * @param param
     * @return
     */
    @Override
    public List<FCTotalVo> totalExport(FinanceCountParam param) {
        return financeCountMapper.totalExport(param);
    }


    /**
     * 获取合计数据
     * @param param
     * @return
     */
    @Override
    public FinanceAmountVo financeCountAmount(FinanceCountParam param) {
        return financeCountMapper.financeCountAmount(param);
    }
}

