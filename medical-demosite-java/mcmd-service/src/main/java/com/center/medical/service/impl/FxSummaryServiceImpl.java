package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxSummaryMapper;
import com.center.medical.bean.model.FxSummary;
import com.center.medical.service.FxSummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次职业健康检查危害因素人员检查情况汇总一览表(FxSummary)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:26
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
    public IPage<FxSummary> getList(PageParam<FxSummary> page, FxSummary param) {
        return fxSummaryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxSummary getInfoById(String id) {
        return fxSummaryMapper.getInfoById(id);
    }

}

