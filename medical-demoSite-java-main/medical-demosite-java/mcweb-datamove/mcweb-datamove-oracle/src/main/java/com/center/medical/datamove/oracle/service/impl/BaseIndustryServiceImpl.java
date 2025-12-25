package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseIndustryMapper;
import com.center.medical.datamove.oracle.bean.model.BaseIndustry;
import com.center.medical.datamove.oracle.service.BaseIndustryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:36
 */
@Slf4j
@Service("baseIndustryService")
@RequiredArgsConstructor
public class BaseIndustryServiceImpl extends ServiceImpl<BaseIndustryMapper, BaseIndustry> implements BaseIndustryService {

    private final BaseIndustryMapper baseIndustryMapper;

    /**
     * 分页查询[国民经济行业分类GB/T 4754—2017]列表
     *
     * @param page  分页参数
     * @param param BaseIndustry查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseIndustry> getPage(PageParam<BaseIndustry> page, BaseIndustry param) {
        return baseIndustryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseIndustry getInfoById(String id) {
        return baseIndustryMapper.getInfoById(id);
    }

}


