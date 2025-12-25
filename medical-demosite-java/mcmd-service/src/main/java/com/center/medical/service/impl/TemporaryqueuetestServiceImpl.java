package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TemporaryqueuetestMapper;
import com.center.medical.bean.model.Temporaryqueuetest;
import com.center.medical.service.TemporaryqueuetestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * temporaryqueuetest(Temporaryqueuetest)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
@Slf4j
@Service("temporaryqueuetestService")
@RequiredArgsConstructor
public class TemporaryqueuetestServiceImpl extends ServiceImpl<TemporaryqueuetestMapper, Temporaryqueuetest> implements TemporaryqueuetestService {

    private final TemporaryqueuetestMapper temporaryqueuetestMapper;

    /**
     * 分页查询[temporaryqueuetest]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueuetest查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Temporaryqueuetest> getList(PageParam<Temporaryqueuetest> page, Temporaryqueuetest param) {
        return temporaryqueuetestMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Temporaryqueuetest getInfoById(String id) {
        return temporaryqueuetestMapper.getInfoById(id);
    }

    ;

}

