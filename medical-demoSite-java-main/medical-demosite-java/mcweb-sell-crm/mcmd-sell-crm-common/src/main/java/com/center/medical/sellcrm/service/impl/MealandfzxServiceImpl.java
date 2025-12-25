package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Mealandfzx;
import com.center.medical.sellcrm.dao.MealandfzxMapper;
import com.center.medical.sellcrm.service.MealandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 普通套餐与分中心关联表(Mealandfzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-21 19:41:48
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

    /**
     * 根据主键id和分中心id查询记录条数
     * @param id
     * @param fzxIds
     * @return
     */
    @Override
    public int countByIdAndFzx(String id, String[] fzxIds) {
        return mealandfzxMapper.countByIdAndFzx(id,fzxIds);
    }
}

