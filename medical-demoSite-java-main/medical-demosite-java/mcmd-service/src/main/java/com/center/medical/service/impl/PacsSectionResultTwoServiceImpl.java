package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PacsSectionResultTwoMapper;
import com.center.medical.bean.model.PacsSectionResultTwo;
import com.center.medical.service.PacsSectionResultTwoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-科室结果表(PacsSectionResultTwo)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:26
 */
@Slf4j
@Service("pacsSectionResultTwoService")
@RequiredArgsConstructor
public class PacsSectionResultTwoServiceImpl extends ServiceImpl<PacsSectionResultTwoMapper, PacsSectionResultTwo> implements PacsSectionResultTwoService {

    private final PacsSectionResultTwoMapper pacsSectionResultTwoMapper;

    /**
     * 分页查询[PACS-科室结果表]列表
     *
     * @param page  分页参数
     * @param param PacsSectionResultTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsSectionResultTwo> getList(PageParam<PacsSectionResultTwo> page, PacsSectionResultTwo param) {
        return pacsSectionResultTwoMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsSectionResultTwo getInfoById(String id) {
        return pacsSectionResultTwoMapper.getInfoById(id);
    }

}

