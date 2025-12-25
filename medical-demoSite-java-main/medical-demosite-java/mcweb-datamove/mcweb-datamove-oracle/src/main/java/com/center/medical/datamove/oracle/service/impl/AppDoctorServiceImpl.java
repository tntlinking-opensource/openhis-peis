package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppDoctorMapper;
import com.center.medical.datamove.oracle.bean.model.AppDoctor;
import com.center.medical.datamove.oracle.service.AppDoctorService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序医生(AppDoctor)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:30
 */
@Slf4j
@Service("appDoctorService")
@RequiredArgsConstructor
public class AppDoctorServiceImpl extends ServiceImpl<AppDoctorMapper, AppDoctor> implements AppDoctorService {

    private final AppDoctorMapper appDoctorMapper;

    /**
     * 分页查询[微信小程序医生]列表
     *
     * @param page  分页参数
     * @param param AppDoctor查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppDoctor> getPage(PageParam<AppDoctor> page, AppDoctor param) {
        return appDoctorMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppDoctor getInfoById(String id) {
        return appDoctorMapper.getInfoById(id);
    }

}


