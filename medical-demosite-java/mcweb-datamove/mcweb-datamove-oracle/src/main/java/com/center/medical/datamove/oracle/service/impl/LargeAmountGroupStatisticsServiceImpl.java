package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.LargeAmountGroupStatisticsMapper;
import com.center.medical.datamove.oracle.bean.model.LargeAmountGroupStatistics;
import com.center.medical.datamove.oracle.service.LargeAmountGroupStatisticsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 年超20万额度单位统计(LargeAmountGroupStatistics)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:35
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
    public IPage<LargeAmountGroupStatistics> getPage(PageParam<LargeAmountGroupStatistics> page, LargeAmountGroupStatistics param) {
        return largeAmountGroupStatisticsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public LargeAmountGroupStatistics getInfoById(String id) {
        return largeAmountGroupStatisticsMapper.getInfoById(id);
    }

}


