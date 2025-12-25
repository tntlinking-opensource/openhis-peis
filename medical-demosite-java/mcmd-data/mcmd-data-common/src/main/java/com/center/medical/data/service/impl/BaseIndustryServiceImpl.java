package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseIndustry;
import com.center.medical.data.dao.BaseIndustryMapper;
import com.center.medical.data.service.BaseIndustryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:51
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
    public IPage<BaseIndustry> getList(PageParam<BaseIndustry> page, BaseIndustry param) {
        return baseIndustryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseIndustry getInfoById(String id) {
        return baseIndustryMapper.getInfoById(id);
    }

}

