package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SectionResultTwoMapper;
import com.center.medical.datamove.oracle.bean.model.SectionResultTwo;
import com.center.medical.datamove.oracle.service.SectionResultTwoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS科室检查结果表————结论词、小结(SectionResultTwo)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:46
 */
@Slf4j
@Service("sectionResultTwoService")
@RequiredArgsConstructor
public class SectionResultTwoServiceImpl extends ServiceImpl<SectionResultTwoMapper, SectionResultTwo> implements SectionResultTwoService {

    private final SectionResultTwoMapper sectionResultTwoMapper;

    /**
     * 分页查询[KS科室检查结果表————结论词、小结]列表
     *
     * @param page  分页参数
     * @param param SectionResultTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionResultTwo> getPage(PageParam<SectionResultTwo> page, SectionResultTwo param) {
        return sectionResultTwoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionResultTwo getInfoById(String id) {
        return sectionResultTwoMapper.getInfoById(id);
    }

}


