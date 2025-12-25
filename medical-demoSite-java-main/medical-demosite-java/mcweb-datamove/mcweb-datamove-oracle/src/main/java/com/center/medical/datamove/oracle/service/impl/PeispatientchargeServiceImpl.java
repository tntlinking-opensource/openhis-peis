package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientchargeMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatientcharge;
import com.center.medical.datamove.oracle.service.PeispatientchargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者缴费表(Peispatientcharge)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:27
 */
@Slf4j
@Service("peispatientchargeService")
@RequiredArgsConstructor
public class PeispatientchargeServiceImpl extends ServiceImpl<PeispatientchargeMapper, Peispatientcharge> implements PeispatientchargeService {

    private final PeispatientchargeMapper peispatientchargeMapper;

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param Peispatientcharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientcharge> getPage(PageParam<Peispatientcharge> page, Peispatientcharge param) {
        return peispatientchargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientcharge getInfoById(String id) {
        return peispatientchargeMapper.getInfoById(id);
    }

}


