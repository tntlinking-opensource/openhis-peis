package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeisReserPaywayMapper;
import com.center.medical.datamove.oracle.bean.model.PeisReserPayway;
import com.center.medical.datamove.oracle.service.PeisReserPaywayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者结算方式表(PeisReserPayway)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:02
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
    public IPage<PeisReserPayway> getPage(PageParam<PeisReserPayway> page, PeisReserPayway param) {
        return peisReserPaywayMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeisReserPayway getInfoById(String id) {
        return peisReserPaywayMapper.getInfoById(id);
    }

}


