package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.StencilMaintainMapper;
import com.center.medical.datamove.oracle.bean.model.StencilMaintain;
import com.center.medical.datamove.oracle.service.StencilMaintainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于保存科室的模板（个检用）、团检的模板、对比模板(StencilMaintain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:06
 */
@Slf4j
@Service("stencilMaintainService")
@RequiredArgsConstructor
public class StencilMaintainServiceImpl extends ServiceImpl<StencilMaintainMapper, StencilMaintain> implements StencilMaintainService {

    private final StencilMaintainMapper stencilMaintainMapper;

    /**
     * 分页查询[用于保存科室的模板（个检用）、团检的模板、对比模板]列表
     *
     * @param page  分页参数
     * @param param StencilMaintain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<StencilMaintain> getPage(PageParam<StencilMaintain> page, StencilMaintain param) {
        return stencilMaintainMapper.getPage(page, param);
    }


}


