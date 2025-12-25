package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TbPatientMapper;
import com.center.medical.datamove.oracle.bean.model.TbPatient;
import com.center.medical.datamove.oracle.service.TbPatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (TbPatient)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:13
 */
@Slf4j
@Service("tbPatientService")
@RequiredArgsConstructor
public class TbPatientServiceImpl extends ServiceImpl<TbPatientMapper, TbPatient> implements TbPatientService {

    private final TbPatientMapper tbPatientMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param TbPatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TbPatient> getPage(PageParam<TbPatient> page, TbPatient param) {
        return tbPatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TbPatient getInfoById(String id) {
        return tbPatientMapper.getInfoById(id);
    }

}


