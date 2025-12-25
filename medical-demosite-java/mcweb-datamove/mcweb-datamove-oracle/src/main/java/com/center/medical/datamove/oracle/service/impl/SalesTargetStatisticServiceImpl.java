package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SalesTargetStatisticMapper;
import com.center.medical.datamove.oracle.bean.model.SalesTargetStatistic;
import com.center.medical.datamove.oracle.service.SalesTargetStatisticService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售目标自动统计(SalesTargetStatistic)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:31
 */
@Slf4j
@Service("salesTargetStatisticService")
@RequiredArgsConstructor
public class SalesTargetStatisticServiceImpl extends ServiceImpl<SalesTargetStatisticMapper, SalesTargetStatistic> implements SalesTargetStatisticService {

    private final SalesTargetStatisticMapper salesTargetStatisticMapper;

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param SalesTargetStatistic查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SalesTargetStatistic> getPage(PageParam<SalesTargetStatistic> page, SalesTargetStatistic param) {
        return salesTargetStatisticMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SalesTargetStatistic getInfoById(String id) {
        return salesTargetStatisticMapper.getInfoById(id);
    }

}


