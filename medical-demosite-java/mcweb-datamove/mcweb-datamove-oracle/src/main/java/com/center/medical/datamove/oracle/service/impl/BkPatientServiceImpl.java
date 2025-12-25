package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BkPatientMapper;
import com.center.medical.datamove.oracle.bean.model.BkPatient;
import com.center.medical.datamove.oracle.service.BkPatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BkPatient)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:49
 */
@Slf4j
@Service("bkPatientService")
@RequiredArgsConstructor
public class BkPatientServiceImpl extends ServiceImpl<BkPatientMapper, BkPatient> implements BkPatientService {

    private final BkPatientMapper bkPatientMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BkPatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BkPatient> getPage(PageParam<BkPatient> page, BkPatient param) {
        return bkPatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BkPatient getInfoById(String id) {
        return bkPatientMapper.getInfoById(id);
    }

}


