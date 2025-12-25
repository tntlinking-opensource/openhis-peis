package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SectionResultMainMapper;
import com.center.medical.datamove.oracle.bean.model.SectionResultMain;
import com.center.medical.datamove.oracle.service.SectionResultMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS科室检查结果主表(SectionResultMain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:43
 */
@Slf4j
@Service("sectionResultMainService")
@RequiredArgsConstructor
public class SectionResultMainServiceImpl extends ServiceImpl<SectionResultMainMapper, SectionResultMain> implements SectionResultMainService {

    private final SectionResultMainMapper sectionResultMainMapper;

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param SectionResultMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionResultMain> getPage(PageParam<SectionResultMain> page, SectionResultMain param) {
        return sectionResultMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionResultMain getInfoById(String id) {
        return sectionResultMainMapper.getInfoById(id);
    }

    ;

}


