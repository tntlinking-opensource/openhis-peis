package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PacsSectionResultMainMapper;
import com.center.medical.bean.model.PacsSectionResultMain;
import com.center.medical.service.PacsSectionResultMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-检查结果主表(PacsSectionResultMain)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:43
 */
@Slf4j
@Service("pacsSectionResultMainService")
@RequiredArgsConstructor
public class PacsSectionResultMainServiceImpl extends ServiceImpl<PacsSectionResultMainMapper, PacsSectionResultMain> implements PacsSectionResultMainService {

    private final PacsSectionResultMainMapper pacsSectionResultMainMapper;

    /**
     * 分页查询[PACS-检查结果主表]列表
     *
     * @param page  分页参数
     * @param param PacsSectionResultMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsSectionResultMain> getList(PageParam<PacsSectionResultMain> page, PacsSectionResultMain param) {
        return pacsSectionResultMainMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsSectionResultMain getInfoById(String id) {
        return pacsSectionResultMainMapper.getInfoById(id);
    }

}

