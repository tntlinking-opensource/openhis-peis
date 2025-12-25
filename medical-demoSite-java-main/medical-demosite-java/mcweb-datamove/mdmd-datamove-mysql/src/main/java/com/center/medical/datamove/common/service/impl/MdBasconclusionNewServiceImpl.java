package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBasconclusionNewMapper;
import com.center.medical.datamove.common.bean.model.MdBasconclusionNew;
import com.center.medical.datamove.common.service.MdBasconclusionNewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 总检结论词(MdBasconclusionNew)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
@Slf4j
@Service("mdBasconclusionNewService")
@RequiredArgsConstructor
public class MdBasconclusionNewServiceImpl extends ServiceImpl<MdBasconclusionNewMapper, MdBasconclusionNew> implements MdBasconclusionNewService {

    private final MdBasconclusionNewMapper mdBasconclusionNewMapper;

    /**
     * 分页查询[总检结论词]列表
     *
     * @param page  分页参数
     * @param param MdBasconclusionNew查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBasconclusionNew> getPage(PageParam<MdBasconclusionNew> page, MdBasconclusionNew param) {
        return mdBasconclusionNewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBasconclusionNew getInfoById(String id) {
        return mdBasconclusionNewMapper.getInfoById(id);
    }

}


