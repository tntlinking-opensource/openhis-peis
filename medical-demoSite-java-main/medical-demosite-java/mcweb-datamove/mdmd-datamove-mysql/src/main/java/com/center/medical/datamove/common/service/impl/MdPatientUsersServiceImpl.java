package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPatientUsersMapper;
import com.center.medical.datamove.common.bean.model.MdPatientUsers;
import com.center.medical.datamove.common.service.MdPatientUsersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检用户账号(MdPatientUsers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
@Slf4j
@Service("mdPatientUsersService")
@RequiredArgsConstructor
public class MdPatientUsersServiceImpl extends ServiceImpl<MdPatientUsersMapper, MdPatientUsers> implements MdPatientUsersService {

    private final MdPatientUsersMapper mdPatientUsersMapper;

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param MdPatientUsers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPatientUsers> getPage(PageParam<MdPatientUsers> page, MdPatientUsers param) {
        return mdPatientUsersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPatientUsers getInfoById(String id) {
        return mdPatientUsersMapper.getInfoById(id);
    }

}


