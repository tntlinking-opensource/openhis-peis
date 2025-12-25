package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.MealandfzxMapper;
import com.center.medical.datamove.oracle.bean.model.Mealandfzx;
import com.center.medical.datamove.oracle.service.MealandfzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 普通套餐与分中心关联表(Mealandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:03
 */
@Slf4j
@Service("mealandfzxService")
@RequiredArgsConstructor
public class MealandfzxServiceImpl extends ServiceImpl<MealandfzxMapper, Mealandfzx> implements MealandfzxService {

    private final MealandfzxMapper mealandfzxMapper;

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Mealandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Mealandfzx> getPage(PageParam<Mealandfzx> page, Mealandfzx param) {
        return mealandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Mealandfzx getInfoById(String id) {
        return mealandfzxMapper.getInfoById(id);
    }

}


