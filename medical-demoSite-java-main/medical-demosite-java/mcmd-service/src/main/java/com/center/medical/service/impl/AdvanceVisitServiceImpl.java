package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.AdvanceVisitMapper;
import com.center.medical.bean.model.AdvanceVisit;
import com.center.medical.service.AdvanceVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF预检跟踪回访记录表(AdvanceVisit)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:21
 */
@Slf4j
@Service("advanceVisitService")
@RequiredArgsConstructor
public class AdvanceVisitServiceImpl extends ServiceImpl<AdvanceVisitMapper, AdvanceVisit> implements AdvanceVisitService {

    private final AdvanceVisitMapper advanceVisitMapper;

    /**
     * 分页查询[KF预检跟踪回访记录表]列表
     *
     * @param page  分页参数
     * @param param AdvanceVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AdvanceVisit> getList(PageParam<AdvanceVisit> page, AdvanceVisit param) {
        return advanceVisitMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public AdvanceVisit getInfoById(String id) {
        return advanceVisitMapper.getInfoById(id);
    }

    ;

}

