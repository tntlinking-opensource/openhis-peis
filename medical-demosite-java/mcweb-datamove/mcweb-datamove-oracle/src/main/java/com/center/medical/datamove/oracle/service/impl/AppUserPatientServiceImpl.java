package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppUserPatientMapper;
import com.center.medical.datamove.oracle.bean.model.AppUserPatient;
import com.center.medical.datamove.oracle.service.AppUserPatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序用户订单关联表(AppUserPatient)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:09
 */
@Slf4j
@Service("appUserPatientService")
@RequiredArgsConstructor
public class AppUserPatientServiceImpl extends ServiceImpl<AppUserPatientMapper, AppUserPatient> implements AppUserPatientService {

    private final AppUserPatientMapper appUserPatientMapper;

    /**
     * 分页查询[小程序用户订单关联表]列表
     *
     * @param page  分页参数
     * @param param AppUserPatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppUserPatient> getPage(PageParam<AppUserPatient> page, AppUserPatient param) {
        return appUserPatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppUserPatient getInfoById(String id) {
        return appUserPatientMapper.getInfoById(id);
    }

}


