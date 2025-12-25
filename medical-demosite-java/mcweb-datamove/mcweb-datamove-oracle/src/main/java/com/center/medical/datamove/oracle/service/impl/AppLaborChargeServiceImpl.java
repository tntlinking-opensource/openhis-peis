package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppLaborChargeMapper;
import com.center.medical.datamove.oracle.bean.model.AppLaborCharge;
import com.center.medical.datamove.oracle.service.AppLaborChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppLaborCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:43
 */
@Slf4j
@Service("appLaborChargeService")
@RequiredArgsConstructor
public class AppLaborChargeServiceImpl extends ServiceImpl<AppLaborChargeMapper, AppLaborCharge> implements AppLaborChargeService {

    private final AppLaborChargeMapper appLaborChargeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppLaborCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppLaborCharge> getPage(PageParam<AppLaborCharge> page, AppLaborCharge param) {
        return appLaborChargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppLaborCharge getInfoById(String id) {
        return appLaborChargeMapper.getInfoById(id);
    }

}


