package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsBasconclusionMapper;
import com.center.medical.datamove.common.bean.model.MdPacsBasconclusion;
import com.center.medical.datamove.common.service.MdPacsBasconclusionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * pacs总检结论词(MdPacsBasconclusion)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
@Slf4j
@Service("mdPacsBasconclusionService")
@RequiredArgsConstructor
public class MdPacsBasconclusionServiceImpl extends ServiceImpl<MdPacsBasconclusionMapper, MdPacsBasconclusion> implements MdPacsBasconclusionService {

    private final MdPacsBasconclusionMapper mdPacsBasconclusionMapper;

    /**
     * 分页查询[pacs总检结论词]列表
     *
     * @param page  分页参数
     * @param param MdPacsBasconclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsBasconclusion> getPage(PageParam<MdPacsBasconclusion> page, MdPacsBasconclusion param) {
        return mdPacsBasconclusionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsBasconclusion getInfoById(String id) {
        return mdPacsBasconclusionMapper.getInfoById(id);
    }

}


