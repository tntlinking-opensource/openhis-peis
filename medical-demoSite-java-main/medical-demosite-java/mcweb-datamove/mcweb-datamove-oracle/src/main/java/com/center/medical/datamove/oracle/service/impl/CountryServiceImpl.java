package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CountryMapper;
import com.center.medical.datamove.oracle.bean.model.Country;
import com.center.medical.datamove.oracle.service.CountryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC国家(Country)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:53
 */
@Slf4j
@Service("countryService")
@RequiredArgsConstructor
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements CountryService {

    private final CountryMapper countryMapper;

    /**
     * 分页查询[JC国家]列表
     *
     * @param page  分页参数
     * @param param Country查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Country> getPage(PageParam<Country> page, Country param) {
        return countryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Country getInfoById(String id) {
        return countryMapper.getInfoById(id);
    }

}


