package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.bean.param.KsIpPageParam;
import com.center.medical.data.dao.KsIpMapper;
import com.center.medical.data.service.KsIpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 科室IP(KsIp)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-04-20 19:37:55
 */
@Slf4j
@Service("ksIpService")
@RequiredArgsConstructor
public class KsIpServiceImpl extends ServiceImpl<KsIpMapper, KsIp> implements KsIpService {

    private final KsIpMapper ksIpMapper;

    /**
     * 分页查询[科室IP]列表
     *
     * @param page  分页参数
     * @param param KsIp查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KsIp> getPage(PageParam<KsIp> page, KsIpPageParam param) {
        return ksIpMapper.getPage(page, param);
    }

    /**
     * 查询[科室IP]列表
     *
     * @param ksId
     * @param branchId
     * @return
     */
    @Override
    public List<KsIp> getList(String ksId, String branchId) {
        return ksIpMapper.getList(ksId, branchId);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public KsIp getInfoById(String id) {
        return ksIpMapper.getInfoById(id);
    }

}

