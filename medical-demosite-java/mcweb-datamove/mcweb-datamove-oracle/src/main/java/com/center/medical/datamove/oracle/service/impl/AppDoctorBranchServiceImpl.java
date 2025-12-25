package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppDoctorBranchMapper;
import com.center.medical.datamove.oracle.bean.model.AppDoctorBranch;
import com.center.medical.datamove.oracle.service.AppDoctorBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序医生分中心关联表(AppDoctorBranch)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:31
 */
@Slf4j
@Service("appDoctorBranchService")
@RequiredArgsConstructor
public class AppDoctorBranchServiceImpl extends ServiceImpl<AppDoctorBranchMapper, AppDoctorBranch> implements AppDoctorBranchService {

    private final AppDoctorBranchMapper appDoctorBranchMapper;

    /**
     * 分页查询[微信小程序医生分中心关联表]列表
     *
     * @param page  分页参数
     * @param param AppDoctorBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppDoctorBranch> getPage(PageParam<AppDoctorBranch> page, AppDoctorBranch param) {
        return appDoctorBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppDoctorBranch getInfoById(String id) {
        return appDoctorBranchMapper.getInfoById(id);
    }

}


