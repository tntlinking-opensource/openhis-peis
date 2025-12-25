package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.MealanditemMapper;
import com.center.medical.datamove.oracle.bean.model.Mealanditem;
import com.center.medical.datamove.oracle.service.MealanditemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 普通套餐与收费项目关联表(Mealanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:04
 */
@Slf4j
@Service("mealanditemService")
@RequiredArgsConstructor
public class MealanditemServiceImpl extends ServiceImpl<MealanditemMapper, Mealanditem> implements MealanditemService {

    private final MealanditemMapper mealanditemMapper;

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Mealanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Mealanditem> getPage(PageParam<Mealanditem> page, Mealanditem param) {
        return mealanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Mealanditem getInfoById(String id) {
        return mealanditemMapper.getInfoById(id);
    }

    ;

}


