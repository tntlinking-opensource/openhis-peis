package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PatientUsersMapper;
import com.center.medical.bean.model.PatientUsers;
import com.center.medical.service.PatientUsersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检用户账号(PatientUsers)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:40
 */
@Slf4j
@Service("patientUsersService")
@RequiredArgsConstructor
public class PatientUsersServiceImpl extends ServiceImpl<PatientUsersMapper, PatientUsers> implements PatientUsersService {

    private final PatientUsersMapper patientUsersMapper;

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param PatientUsers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PatientUsers> getList(PageParam<PatientUsers> page, PatientUsers param) {
        return patientUsersMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PatientUsers getInfoById(String id) {
        return patientUsersMapper.getInfoById(id);
    }

    ;

}

