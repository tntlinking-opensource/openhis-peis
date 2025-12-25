package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.param.PoGroupParam;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import com.center.medical.sellcrm.bean.param.DbOrderParam;
import com.center.medical.sellcrm.bean.vo.POSimpleVo;
import com.center.medical.sellcrm.dao.PeisorgreservationMapper;
import com.center.medical.sellcrm.service.PeisorgreservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 体检者团体任务(Peisorgreservation)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:01
 */
@Slf4j
@Service("peisorgreservationService")
@RequiredArgsConstructor
public class PeisorgreservationServiceImpl extends ServiceImpl<PeisorgreservationMapper, Peisorgreservation> implements PeisorgreservationService {

    private final PeisorgreservationMapper peisorgreservationMapper;

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peisorgreservation> getPage(PageParam<Peisorgreservation> page, DbOrderParam param) {

        return peisorgreservationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peisorgreservation getInfoById(String id) {
        return peisorgreservationMapper.getInfoById(id);
    }

    /**
     * 登记页面团体列表
     *
     * @param param
     * @return
     */
    @Override
    public IPage<POSimpleVo> getGroupForOrgData(PageParam<Peisorgreservation> page, PoGroupParam param) {
        param.setBranchId(SecurityUtils.getCId());
        return peisorgreservationMapper.getGroupForOrgData(page, param);
    }

}

