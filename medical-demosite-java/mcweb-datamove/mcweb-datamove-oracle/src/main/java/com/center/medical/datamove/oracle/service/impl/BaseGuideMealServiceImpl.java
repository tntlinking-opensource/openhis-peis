package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseGuideMealMapper;
import com.center.medical.datamove.oracle.bean.model.BaseGuideMeal;
import com.center.medical.datamove.oracle.service.BaseGuideMealService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BaseGuideMeal)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:33
 */
@Slf4j
@Service("baseGuideMealService")
@RequiredArgsConstructor
public class BaseGuideMealServiceImpl extends ServiceImpl<BaseGuideMealMapper, BaseGuideMeal> implements BaseGuideMealService {

    private final BaseGuideMealMapper baseGuideMealMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMeal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseGuideMeal> getPage(PageParam<BaseGuideMeal> page, BaseGuideMeal param) {
        return baseGuideMealMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseGuideMeal getInfoById(String id) {
        return baseGuideMealMapper.getInfoById(id);
    }

}


