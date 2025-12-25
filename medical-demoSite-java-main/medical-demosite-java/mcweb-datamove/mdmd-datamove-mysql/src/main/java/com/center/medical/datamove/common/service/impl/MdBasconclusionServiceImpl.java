package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBasconclusionMapper;
import com.center.medical.datamove.common.bean.model.MdBasconclusion;
import com.center.medical.datamove.common.service.MdBasconclusionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 总检结论词(MdBasconclusion)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
@Slf4j
@Service("mdBasconclusionService")
@RequiredArgsConstructor
public class MdBasconclusionServiceImpl extends ServiceImpl<MdBasconclusionMapper, MdBasconclusion> implements MdBasconclusionService {

    private final MdBasconclusionMapper mdBasconclusionMapper;

    /**
     * 分页查询[总检结论词]列表
     *
     * @param page  分页参数
     * @param param MdBasconclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBasconclusion> getPage(PageParam<MdBasconclusion> page, MdBasconclusion param) {
        return mdBasconclusionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBasconclusion getInfoById(String id) {
        return mdBasconclusionMapper.getInfoById(id);
    }

}


