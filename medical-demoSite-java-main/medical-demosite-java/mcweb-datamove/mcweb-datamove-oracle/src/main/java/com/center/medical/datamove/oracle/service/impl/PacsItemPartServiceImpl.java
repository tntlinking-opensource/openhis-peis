package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsItemPartMapper;
import com.center.medical.datamove.oracle.bean.model.PacsItemPart;
import com.center.medical.datamove.oracle.service.PacsItemPartService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsItemPart)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:45
 */
@Slf4j
@Service("pacsItemPartService")
@RequiredArgsConstructor
public class PacsItemPartServiceImpl extends ServiceImpl<PacsItemPartMapper, PacsItemPart> implements PacsItemPartService {

    private final PacsItemPartMapper pacsItemPartMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsItemPart查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsItemPart> getPage(PageParam<PacsItemPart> page, PacsItemPart param) {
        return pacsItemPartMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsItemPart getInfoById(String id) {
        return pacsItemPartMapper.getInfoById(id);
    }

}


