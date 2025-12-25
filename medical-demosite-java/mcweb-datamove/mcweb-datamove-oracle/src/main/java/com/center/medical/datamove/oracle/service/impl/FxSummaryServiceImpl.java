package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxSummaryMapper;
import com.center.medical.datamove.oracle.bean.model.FxSummary;
import com.center.medical.datamove.oracle.service.FxSummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次职业健康检查危害因素人员检查情况汇总一览表(FxSummary)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:01
 */
@Slf4j
@Service("fxSummaryService")
@RequiredArgsConstructor
public class FxSummaryServiceImpl extends ServiceImpl<FxSummaryMapper, FxSummary> implements FxSummaryService {

    private final FxSummaryMapper fxSummaryMapper;

    /**
     * 分页查询[本次职业健康检查危害因素人员检查情况汇总一览表]列表
     *
     * @param page  分页参数
     * @param param FxSummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxSummary> getPage(PageParam<FxSummary> page, FxSummary param) {
        return fxSummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxSummary getInfoById(String id) {
        return fxSummaryMapper.getInfoById(id);
    }

    ;

}


