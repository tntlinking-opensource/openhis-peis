package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsBasePartMapper;
import com.center.medical.datamove.oracle.bean.model.PacsBasePart;
import com.center.medical.datamove.oracle.service.PacsBasePartService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsBasePart)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:40
 */
@Slf4j
@Service("pacsBasePartService")
@RequiredArgsConstructor
public class PacsBasePartServiceImpl extends ServiceImpl<PacsBasePartMapper, PacsBasePart> implements PacsBasePartService {

    private final PacsBasePartMapper pacsBasePartMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsBasePart查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasePart> getPage(PageParam<PacsBasePart> page, PacsBasePart param) {
        return pacsBasePartMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsBasePart getInfoById(String id) {
        return pacsBasePartMapper.getInfoById(id);
    }

}


