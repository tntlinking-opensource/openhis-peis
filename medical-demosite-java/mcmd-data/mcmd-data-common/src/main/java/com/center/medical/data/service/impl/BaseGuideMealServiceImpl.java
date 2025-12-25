package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseGuideMeal;
import com.center.medical.data.dao.BaseGuideMealMapper;
import com.center.medical.data.service.BaseGuideMealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 基础推荐套餐(BaseGuideMeal)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:14
 */
@Slf4j
@Service("baseGuideMealService")
@RequiredArgsConstructor
public class BaseGuideMealServiceImpl extends ServiceImpl<BaseGuideMealMapper, BaseGuideMeal> implements BaseGuideMealService {

    private final BaseGuideMealMapper baseGuideMealMapper;

    /**
     * 分页查询[基础推荐套餐]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMeal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseGuideMeal> getList(PageParam<BaseGuideMeal> page, BaseGuideMeal param) {
        return baseGuideMealMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseGuideMeal getInfoById(String id) {
        return baseGuideMealMapper.getInfoById(id);
    }

}

