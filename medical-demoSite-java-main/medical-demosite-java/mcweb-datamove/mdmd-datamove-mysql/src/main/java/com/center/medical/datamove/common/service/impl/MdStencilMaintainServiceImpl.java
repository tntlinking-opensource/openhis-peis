package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdStencilMaintainMapper;
import com.center.medical.datamove.common.bean.model.MdStencilMaintain;
import com.center.medical.datamove.common.service.MdStencilMaintainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(MdStencilMaintain)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:48
 */
@Slf4j
@Service("mdStencilMaintainService")
@RequiredArgsConstructor
public class MdStencilMaintainServiceImpl extends ServiceImpl<MdStencilMaintainMapper, MdStencilMaintain> implements MdStencilMaintainService {

    private final MdStencilMaintainMapper mdStencilMaintainMapper;

    /**
     * 分页查询[模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)]列表
     *
     * @param page  分页参数
     * @param param MdStencilMaintain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdStencilMaintain> getPage(PageParam<MdStencilMaintain> page, MdStencilMaintain param) {
        return mdStencilMaintainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdStencilMaintain getInfoById(String id) {
        return mdStencilMaintainMapper.getInfoById(id);
    }

}


