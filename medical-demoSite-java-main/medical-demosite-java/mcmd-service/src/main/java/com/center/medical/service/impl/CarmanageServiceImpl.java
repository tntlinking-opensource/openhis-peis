package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.CarmanageMapper;
import com.center.medical.bean.model.Carmanage;
import com.center.medical.service.CarmanageService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检车管理(Carmanage)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:35
 */
@Slf4j
@Service("carmanageService")
@RequiredArgsConstructor
public class CarmanageServiceImpl extends ServiceImpl<CarmanageMapper, Carmanage> implements CarmanageService {

    private final CarmanageMapper carmanageMapper;

    /**
     * 分页查询[体检车管理]列表
     *
     * @param page  分页参数
     * @param param Carmanage查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Carmanage> getList(PageParam<Carmanage> page, Carmanage param) {
        return carmanageMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Carmanage getInfoById(String id) {
        return carmanageMapper.getInfoById(id);
    }

    ;

}

