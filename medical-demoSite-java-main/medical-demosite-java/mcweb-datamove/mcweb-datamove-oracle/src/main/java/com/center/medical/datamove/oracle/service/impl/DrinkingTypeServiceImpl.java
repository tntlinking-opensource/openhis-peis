package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DrinkingTypeMapper;
import com.center.medical.datamove.oracle.bean.model.DrinkingType;
import com.center.medical.datamove.oracle.service.DrinkingTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DrinkingType)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:22
 */
@Slf4j
@Service("drinkingTypeService")
@RequiredArgsConstructor
public class DrinkingTypeServiceImpl extends ServiceImpl<DrinkingTypeMapper, DrinkingType> implements DrinkingTypeService {

    private final DrinkingTypeMapper drinkingTypeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DrinkingType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrinkingType> getPage(PageParam<DrinkingType> page, DrinkingType param) {
        return drinkingTypeMapper.getPage(page, param);
    }


}


