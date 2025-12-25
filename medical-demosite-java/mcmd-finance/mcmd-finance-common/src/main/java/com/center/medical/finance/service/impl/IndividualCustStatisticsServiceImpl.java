package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ICPageParam;
import com.center.medical.finance.bean.vo.ICPageVo;
import com.center.medical.finance.dao.IndividualCustStatisticsMapper;
import com.center.medical.finance.service.IndividualCustStatisticsService;
import com.center.medical.system.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 客服销售统计(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-06 16:22:37
 */
@Slf4j
@Service("individualCustStatisticsService")
@RequiredArgsConstructor
public class IndividualCustStatisticsServiceImpl extends ServiceImpl<IndividualCustStatisticsMapper, Peispatient> implements IndividualCustStatisticsService {

    private final IndividualCustStatisticsMapper individualCustStatisticsMapper;
    private final BranchService branchService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ICPageVo> getList(PageParam<ICPageVo> page, ICPageParam param) {
        //线上没有搜索条件给一个默认的分中心
        boolean online = ZhongkangConfig.isOnline();
        if (online && ObjectUtils.isNotEmpty(param) && CollectionUtils.isEmpty(param.getBranchIds())) {
            param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
        }
        return individualCustStatisticsMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return individualCustStatisticsMapper.getInfoById(id);
    }


    /**
     * 导出客服销售统计
     *
     * @param param
     * @return
     */
    @Override
    public List<ICPageVo> getExportData(ICPageParam param) {
        //线上没有搜索条件给一个默认的分中心
        boolean online = ZhongkangConfig.isOnline();
        if (online && ObjectUtils.isNotEmpty(param) && CollectionUtils.isEmpty(param.getBranchIds())) {
            param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
        }
        List<ICPageVo> list = individualCustStatisticsMapper.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            ICPageVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }
}

