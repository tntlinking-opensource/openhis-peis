package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TemporaryqueuetestMapper;
import com.center.medical.datamove.oracle.bean.model.Temporaryqueuetest;
import com.center.medical.datamove.oracle.service.TemporaryqueuetestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Temporaryqueuetest)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:20
 */
@Slf4j
@Service("temporaryqueuetestService")
@RequiredArgsConstructor
public class TemporaryqueuetestServiceImpl extends ServiceImpl<TemporaryqueuetestMapper, Temporaryqueuetest> implements TemporaryqueuetestService {

    private final TemporaryqueuetestMapper temporaryqueuetestMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueuetest查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Temporaryqueuetest> getPage(PageParam<Temporaryqueuetest> page, Temporaryqueuetest param) {
        return temporaryqueuetestMapper.getPage(page, param);
    }


}


