package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BasMergeMapper;
import com.center.medical.datamove.oracle.bean.model.BasMerge;
import com.center.medical.datamove.oracle.service.BasMergeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BasMerge)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:20
 */
@Slf4j
@Service("basMergeService")
@RequiredArgsConstructor
public class BasMergeServiceImpl extends ServiceImpl<BasMergeMapper, BasMerge> implements BasMergeService {

    private final BasMergeMapper basMergeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BasMerge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BasMerge> getPage(PageParam<BasMerge> page, BasMerge param) {
        return basMergeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BasMerge getInfoById(String id) {
        return basMergeMapper.getInfoById(id);
    }

}


