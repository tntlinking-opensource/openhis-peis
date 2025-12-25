package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.CarmanagefrMapper;
import com.center.medical.bean.model.Carmanagefr;
import com.center.medical.service.CarmanagefrService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检车与外出体检车上的人员关联表(Carmanagefr)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
@Slf4j
@Service("carmanagefrService")
@RequiredArgsConstructor
public class CarmanagefrServiceImpl extends ServiceImpl<CarmanagefrMapper, Carmanagefr> implements CarmanagefrService {

    private final CarmanagefrMapper carmanagefrMapper;

    /**
     * 分页查询[体检车与外出体检车上的人员关联表]列表
     *
     * @param page  分页参数
     * @param param Carmanagefr查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Carmanagefr> getList(PageParam<Carmanagefr> page, Carmanagefr param) {
        return carmanagefrMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Carmanagefr getInfoById(String id) {
        return carmanagefrMapper.getInfoById(id);
    }

    ;

}

