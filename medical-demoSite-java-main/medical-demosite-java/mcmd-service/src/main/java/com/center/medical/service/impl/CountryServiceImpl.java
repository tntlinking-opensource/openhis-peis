package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.CountryMapper;
import com.center.medical.bean.model.Country;
import com.center.medical.service.CountryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC国家(Country)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:28
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
    public IPage<Country> getList(PageParam<Country> page, Country param) {
        return countryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Country getInfoById(String id) {
        return countryMapper.getInfoById(id);
    }

    ;

}

