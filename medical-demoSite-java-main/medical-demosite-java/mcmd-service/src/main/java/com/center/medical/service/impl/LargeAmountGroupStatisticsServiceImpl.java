package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.LargeAmountGroupStatisticsMapper;
import com.center.medical.bean.model.LargeAmountGroupStatistics;
import com.center.medical.service.LargeAmountGroupStatisticsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 年超20万额度单位统计(LargeAmountGroupStatistics)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:19
 */
@Slf4j
@Service("largeAmountGroupStatisticsService")
@RequiredArgsConstructor
public class LargeAmountGroupStatisticsServiceImpl extends ServiceImpl<LargeAmountGroupStatisticsMapper, LargeAmountGroupStatistics> implements LargeAmountGroupStatisticsService {

    private final LargeAmountGroupStatisticsMapper largeAmountGroupStatisticsMapper;

    /**
     * 分页查询[年超20万额度单位统计]列表
     *
     * @param page  分页参数
     * @param param LargeAmountGroupStatistics查询参数
     * @return 分页数据
     */
    @Override
    public IPage<LargeAmountGroupStatistics> getList(PageParam<LargeAmountGroupStatistics> page, LargeAmountGroupStatistics param) {
        return largeAmountGroupStatisticsMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public LargeAmountGroupStatistics getInfoById(String id) {
        return largeAmountGroupStatisticsMapper.getInfoById(id);
    }

}

