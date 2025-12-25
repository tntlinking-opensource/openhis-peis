package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseIndustryMapper;
import com.center.medical.datamove.common.bean.model.MdBaseIndustry;
import com.center.medical.datamove.common.service.MdBaseIndustryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 国民经济行业分类GB/T 4754—2017(MdBaseIndustry)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Slf4j
@Service("mdBaseIndustryService")
@RequiredArgsConstructor
public class MdBaseIndustryServiceImpl extends ServiceImpl<MdBaseIndustryMapper, MdBaseIndustry> implements MdBaseIndustryService {

    private final MdBaseIndustryMapper mdBaseIndustryMapper;

    /**
     * 分页查询[国民经济行业分类GB/T 4754—2017]列表
     *
     * @param page  分页参数
     * @param param MdBaseIndustry查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseIndustry> getPage(PageParam<MdBaseIndustry> page, MdBaseIndustry param) {
        return mdBaseIndustryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseIndustry getInfoById(String id) {
        return mdBaseIndustryMapper.getInfoById(id);
    }

}


