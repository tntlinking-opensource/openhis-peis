package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ZySummaryMapper;
import com.center.medical.datamove.oracle.bean.model.ZySummary;
import com.center.medical.datamove.oracle.service.ZySummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病检查结论(ZySummary)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:31:02
 */
@Slf4j
@Service("zySummaryService")
@RequiredArgsConstructor
public class ZySummaryServiceImpl extends ServiceImpl<ZySummaryMapper, ZySummary> implements ZySummaryService {

    private final ZySummaryMapper zySummaryMapper;

    /**
     * 分页查询[JC职业病检查结论]列表
     *
     * @param page  分页参数
     * @param param ZySummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZySummary> getPage(PageParam<ZySummary> page, ZySummary param) {
        return zySummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ZySummary getInfoById(String id) {
        return zySummaryMapper.getInfoById(id);
    }

}


