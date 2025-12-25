package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FailTotalVisitMapper;
import com.center.medical.bean.model.FailTotalVisit;
import com.center.medical.service.FailTotalVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF迟捡、阳性、不合格样本回访(FailTotalVisit)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:10
 */
@Slf4j
@Service("failTotalVisitService")
@RequiredArgsConstructor
public class FailTotalVisitServiceImpl extends ServiceImpl<FailTotalVisitMapper, FailTotalVisit> implements FailTotalVisitService {

    private final FailTotalVisitMapper failTotalVisitMapper;

    /**
     * 分页查询[KF迟捡、阳性、不合格样本回访]列表
     *
     * @param page  分页参数
     * @param param FailTotalVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FailTotalVisit> getList(PageParam<FailTotalVisit> page, FailTotalVisit param) {
        return failTotalVisitMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FailTotalVisit getInfoById(String id) {
        return failTotalVisitMapper.getInfoById(id);
    }

    ;

}

