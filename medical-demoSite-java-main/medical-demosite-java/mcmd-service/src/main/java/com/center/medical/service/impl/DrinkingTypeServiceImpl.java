package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DrinkingTypeMapper;
import com.center.medical.bean.model.DrinkingType;
import com.center.medical.service.DrinkingTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 饮酒种类(DrinkingType)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
 */
@Slf4j
@Service("drinkingTypeService")
@RequiredArgsConstructor
public class DrinkingTypeServiceImpl extends ServiceImpl<DrinkingTypeMapper, DrinkingType> implements DrinkingTypeService {

    private final DrinkingTypeMapper drinkingTypeMapper;

    /**
     * 分页查询[饮酒种类]列表
     *
     * @param page  分页参数
     * @param param DrinkingType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrinkingType> getList(PageParam<DrinkingType> page, DrinkingType param) {
        return drinkingTypeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DrinkingType getInfoById(String id) {
        return drinkingTypeMapper.getInfoById(id);
    }

    ;

}

