package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsPdfMapper;
import com.center.medical.datamove.oracle.bean.model.PacsPdf;
import com.center.medical.datamove.oracle.service.PacsPdfService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS PDF  海康医院使用(PacsPdf)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:48
 */
@Slf4j
@Service("pacsPdfService")
@RequiredArgsConstructor
public class PacsPdfServiceImpl extends ServiceImpl<PacsPdfMapper, PacsPdf> implements PacsPdfService {

    private final PacsPdfMapper pacsPdfMapper;

    /**
     * 分页查询[PACS PDF  海康医院使用]列表
     *
     * @param page  分页参数
     * @param param PacsPdf查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsPdf> getPage(PageParam<PacsPdf> page, PacsPdf param) {
        return pacsPdfMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsPdf getInfoById(String id) {
        return pacsPdfMapper.getInfoById(id);
    }

}


