package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppDoctorLabelMapper;
import com.center.medical.datamove.oracle.bean.model.AppDoctorLabel;
import com.center.medical.datamove.oracle.service.AppDoctorLabelService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序医生标签关联表(AppDoctorLabel)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:33
 */
@Slf4j
@Service("appDoctorLabelService")
@RequiredArgsConstructor
public class AppDoctorLabelServiceImpl extends ServiceImpl<AppDoctorLabelMapper, AppDoctorLabel> implements AppDoctorLabelService {

    private final AppDoctorLabelMapper appDoctorLabelMapper;

    /**
     * 分页查询[微信小程序医生标签关联表]列表
     *
     * @param page  分页参数
     * @param param AppDoctorLabel查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppDoctorLabel> getPage(PageParam<AppDoctorLabel> page, AppDoctorLabel param) {
        return appDoctorLabelMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppDoctorLabel getInfoById(String id) {
        return appDoctorLabelMapper.getInfoById(id);
    }

}


