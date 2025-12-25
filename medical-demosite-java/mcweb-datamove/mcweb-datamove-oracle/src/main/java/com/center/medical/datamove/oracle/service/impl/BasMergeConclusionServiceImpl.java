package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BasMergeConclusionMapper;
import com.center.medical.datamove.oracle.bean.model.BasMergeConclusion;
import com.center.medical.datamove.oracle.service.BasMergeConclusionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BasMergeConclusion)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:21
 */
@Slf4j
@Service("basMergeConclusionService")
@RequiredArgsConstructor
public class BasMergeConclusionServiceImpl extends ServiceImpl<BasMergeConclusionMapper, BasMergeConclusion> implements BasMergeConclusionService {

    private final BasMergeConclusionMapper basMergeConclusionMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BasMergeConclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BasMergeConclusion> getPage(PageParam<BasMergeConclusion> page, BasMergeConclusion param) {
        return basMergeConclusionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BasMergeConclusion getInfoById(String id) {
        return basMergeConclusionMapper.getInfoById(id);
    }

}


