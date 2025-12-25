package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PacsBasconclusionMapper;
import com.center.medical.bean.model.PacsBasconclusion;
import com.center.medical.service.PacsBasconclusionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * pacs总检结论词(PacsBasconclusion)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:15
 */
@Slf4j
@Service("pacsBasconclusionService")
@RequiredArgsConstructor
public class PacsBasconclusionServiceImpl extends ServiceImpl<PacsBasconclusionMapper, PacsBasconclusion> implements PacsBasconclusionService {

    private final PacsBasconclusionMapper pacsBasconclusionMapper;

    /**
     * 分页查询[pacs总检结论词]列表
     *
     * @param page  分页参数
     * @param param PacsBasconclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasconclusion> getList(PageParam<PacsBasconclusion> page, PacsBasconclusion param) {
        return pacsBasconclusionMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsBasconclusion getInfoById(String id) {
        return pacsBasconclusionMapper.getInfoById(id);
    }

    ;

}

