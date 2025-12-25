package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdZyVsSummaryMapper;
import com.center.medical.datamove.common.bean.model.MdZyVsSummary;
import com.center.medical.datamove.common.service.MdZyVsSummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病处理意见(MdZyVsSummary)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
@Slf4j
@Service("mdZyVsSummaryService")
@RequiredArgsConstructor
public class MdZyVsSummaryServiceImpl extends ServiceImpl<MdZyVsSummaryMapper, MdZyVsSummary> implements MdZyVsSummaryService {

    private final MdZyVsSummaryMapper mdZyVsSummaryMapper;

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param MdZyVsSummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdZyVsSummary> getPage(PageParam<MdZyVsSummary> page, MdZyVsSummary param) {
        return mdZyVsSummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdZyVsSummary getInfoById(String id) {
        return mdZyVsSummaryMapper.getInfoById(id);
    }

}


