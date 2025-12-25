package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboandfzx;
import com.center.medical.sellcrm.dao.ComboandfzxMapper;
import com.center.medical.sellcrm.service.ComboandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 最小套餐与分中心关联表(Comboandfzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:49
 */
@Slf4j
@Service("comboandfzxService")
@RequiredArgsConstructor
public class ComboandfzxServiceImpl extends ServiceImpl<ComboandfzxMapper, Comboandfzx> implements ComboandfzxService {

    private final ComboandfzxMapper comboandfzxMapper;

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Comboandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Comboandfzx> getList(PageParam<Comboandfzx> page, Comboandfzx param) {
        return comboandfzxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Comboandfzx getInfoById(String id) {
        return comboandfzxMapper.getInfoById(id);
    }

}

