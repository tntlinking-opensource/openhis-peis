package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Comboandfzx;
import com.center.medical.datamove.oracle.dao.ComboandfzxMapper;
import com.center.medical.datamove.oracle.service.ComboandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 最小套餐与分中心关联表(Comboandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:40
 */
@Slf4j
@Service("comboandfzxsService")
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
    public IPage<Comboandfzx> getPage(PageParam<Comboandfzx> page, Comboandfzx param) {
        return comboandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Comboandfzx getInfoById(String id) {
        return comboandfzxMapper.getInfoById(id);
    }

}


