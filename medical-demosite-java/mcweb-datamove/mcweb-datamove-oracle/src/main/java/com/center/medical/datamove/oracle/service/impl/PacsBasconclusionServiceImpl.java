package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsBasconclusionMapper;
import com.center.medical.datamove.oracle.bean.model.PacsBasconclusion;
import com.center.medical.datamove.oracle.service.PacsBasconclusionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsBasconclusion)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:39
 */
@Slf4j
@Service("pacsBasconclusionService")
@RequiredArgsConstructor
public class PacsBasconclusionServiceImpl extends ServiceImpl<PacsBasconclusionMapper, PacsBasconclusion> implements PacsBasconclusionService {

    private final PacsBasconclusionMapper pacsBasconclusionMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsBasconclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasconclusion> getPage(PageParam<PacsBasconclusion> page, PacsBasconclusion param) {
        return pacsBasconclusionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsBasconclusion getInfoById(String id) {
        return pacsBasconclusionMapper.getInfoById(id);
    }

}


