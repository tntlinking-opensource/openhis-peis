package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PeisReserPayway;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeisReserPaywayMapper;
import com.center.medical.service.PeisReserPaywayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者结算方式表(PeisReserPayway)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
@Slf4j
@Service("peisReserPaywayService")
@RequiredArgsConstructor
public class PeisReserPaywayServiceImpl extends ServiceImpl<PeisReserPaywayMapper, PeisReserPayway> implements PeisReserPaywayService {

    private final PeisReserPaywayMapper peisReserPaywayMapper;

    /**
     * 分页查询[体检者结算方式表]列表
     *
     * @param page  分页参数
     * @param param PeisReserPayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisReserPayway> getList(PageParam<PeisReserPayway> page, PeisReserPayway param) {
        return peisReserPaywayMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeisReserPayway getInfoById(String id) {
        return peisReserPaywayMapper.getInfoById(id);
    }


    /**
     * 获取结算信息
     *
     * @param id
     * @return
     */
    @Override
    public List<PeisReserPayway> getBillingData(String id) {
        return peisReserPaywayMapper.getBillingData(id);
    }
}

