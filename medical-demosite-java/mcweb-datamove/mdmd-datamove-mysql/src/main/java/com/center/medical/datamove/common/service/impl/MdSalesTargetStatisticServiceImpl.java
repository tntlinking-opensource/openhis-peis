package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSalesTargetStatisticMapper;
import com.center.medical.datamove.common.bean.model.MdSalesTargetStatistic;
import com.center.medical.datamove.common.service.MdSalesTargetStatisticService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售目标自动统计(MdSalesTargetStatistic)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:22
 */
@Slf4j
@Service("mdSalesTargetStatisticService")
@RequiredArgsConstructor
public class MdSalesTargetStatisticServiceImpl extends ServiceImpl<MdSalesTargetStatisticMapper, MdSalesTargetStatistic> implements MdSalesTargetStatisticService {

    private final MdSalesTargetStatisticMapper mdSalesTargetStatisticMapper;

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param MdSalesTargetStatistic查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSalesTargetStatistic> getPage(PageParam<MdSalesTargetStatistic> page, MdSalesTargetStatistic param) {
        return mdSalesTargetStatisticMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSalesTargetStatistic getInfoById(String id) {
        return mdSalesTargetStatisticMapper.getInfoById(id);
    }

}


