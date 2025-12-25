package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsSectionResultTwoMapper;
import com.center.medical.datamove.oracle.bean.model.PacsSectionResultTwo;
import com.center.medical.datamove.oracle.service.PacsSectionResultTwoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsSectionResultTwo)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:55
 */
@Slf4j
@Service("pacsSectionResultTwoService")
@RequiredArgsConstructor
public class PacsSectionResultTwoServiceImpl extends ServiceImpl<PacsSectionResultTwoMapper, PacsSectionResultTwo> implements PacsSectionResultTwoService {

    private final PacsSectionResultTwoMapper pacsSectionResultTwoMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsSectionResultTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsSectionResultTwo> getPage(PageParam<PacsSectionResultTwo> page, PacsSectionResultTwo param) {
        return pacsSectionResultTwoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsSectionResultTwo getInfoById(String id) {
        return pacsSectionResultTwoMapper.getInfoById(id);
    }

}


