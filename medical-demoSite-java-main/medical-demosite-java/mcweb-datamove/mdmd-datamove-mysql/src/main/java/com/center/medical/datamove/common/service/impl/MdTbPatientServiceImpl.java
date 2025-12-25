package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTbPatientMapper;
import com.center.medical.datamove.common.bean.model.MdTbPatient;
import com.center.medical.datamove.common.service.MdTbPatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步主表(MdTbPatient)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:54
 */
@Slf4j
@Service("mdTbPatientService")
@RequiredArgsConstructor
public class MdTbPatientServiceImpl extends ServiceImpl<MdTbPatientMapper, MdTbPatient> implements MdTbPatientService {

    private final MdTbPatientMapper mdTbPatientMapper;

    /**
     * 分页查询[同步主表]列表
     *
     * @param page  分页参数
     * @param param MdTbPatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTbPatient> getPage(PageParam<MdTbPatient> page, MdTbPatient param) {
        return mdTbPatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTbPatient getInfoById(String id) {
        return mdTbPatientMapper.getInfoById(id);
    }

}


