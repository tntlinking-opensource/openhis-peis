package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BasconclusionMapper;
import com.center.medical.datamove.oracle.bean.model.Basconclusion;
import com.center.medical.datamove.oracle.service.BasconclusionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Basconclusion)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:23
 */
@Slf4j
@Service("basconclusionService")
@RequiredArgsConstructor
public class BasconclusionServiceImpl extends ServiceImpl<BasconclusionMapper, Basconclusion> implements BasconclusionService {

    private final BasconclusionMapper basconclusionMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Basconclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Basconclusion> getPage(PageParam<Basconclusion> page, Basconclusion param) {
        return basconclusionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Basconclusion getInfoById(String id) {
        return basconclusionMapper.getInfoById(id);
    }

}


