package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsSectionResultMainMapper;
import com.center.medical.datamove.oracle.bean.model.PacsSectionResultMain;
import com.center.medical.datamove.oracle.service.PacsSectionResultMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsSectionResultMain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:53
 */
@Slf4j
@Service("pacsSectionResultMainService")
@RequiredArgsConstructor
public class PacsSectionResultMainServiceImpl extends ServiceImpl<PacsSectionResultMainMapper, PacsSectionResultMain> implements PacsSectionResultMainService {

    private final PacsSectionResultMainMapper pacsSectionResultMainMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsSectionResultMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsSectionResultMain> getPage(PageParam<PacsSectionResultMain> page, PacsSectionResultMain param) {
        return pacsSectionResultMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsSectionResultMain getInfoById(String id) {
        return pacsSectionResultMainMapper.getInfoById(id);
    }

}


