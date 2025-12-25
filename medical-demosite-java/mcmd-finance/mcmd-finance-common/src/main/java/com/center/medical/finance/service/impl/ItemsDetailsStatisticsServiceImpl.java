package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ItemsDetailsStatisticsParam;
import com.center.medical.finance.bean.vo.ItemsDetailsStatisticsVo;
import com.center.medical.finance.dao.ItemsDetailsStatisticsMapper;
import com.center.medical.finance.service.ItemsDetailsStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报告信息查询(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("itemsDetailsStatisticsService")
@RequiredArgsConstructor
public class ItemsDetailsStatisticsServiceImpl extends ServiceImpl<ItemsDetailsStatisticsMapper, Peispatient> implements ItemsDetailsStatisticsService {

    private final ItemsDetailsStatisticsMapper itemsDetailsStatisticsMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ItemsDetailsStatisticsVo> getList(PageParam<ItemsDetailsStatisticsVo> page, ItemsDetailsStatisticsParam param) {
        return itemsDetailsStatisticsMapper.getList(page, param);
    }


    /**
     * 导出报告信息
     *
     * @param param
     * @return
     */
    @Override
    public List<ItemsDetailsStatisticsVo> getExportData(ItemsDetailsStatisticsParam param) {
        return itemsDetailsStatisticsMapper.getExportData(param);
    }

}

