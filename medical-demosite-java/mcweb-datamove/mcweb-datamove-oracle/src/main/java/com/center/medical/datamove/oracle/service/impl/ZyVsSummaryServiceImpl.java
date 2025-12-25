package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ZyVsSummaryMapper;
import com.center.medical.datamove.oracle.bean.model.ZyVsSummary;
import com.center.medical.datamove.oracle.service.ZyVsSummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病处理意见(ZyVsSummary)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:31:04
 */
@Slf4j
@Service("zyVsSummaryService")
@RequiredArgsConstructor
public class ZyVsSummaryServiceImpl extends ServiceImpl<ZyVsSummaryMapper, ZyVsSummary> implements ZyVsSummaryService {

    private final ZyVsSummaryMapper zyVsSummaryMapper;

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param ZyVsSummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyVsSummary> getPage(PageParam<ZyVsSummary> page, ZyVsSummary param) {
        return zyVsSummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ZyVsSummary getInfoById(String id) {
        return zyVsSummaryMapper.getInfoById(id);
    }

}


