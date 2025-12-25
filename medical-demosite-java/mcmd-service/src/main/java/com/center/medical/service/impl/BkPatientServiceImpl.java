package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.param.BkPatientParam;
import com.center.medical.dao.BkPatientMapper;
import com.center.medical.bean.model.BkPatient;
import com.center.medical.service.BkPatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者(BkPatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
@Slf4j
@Service("bkPatientService")
@RequiredArgsConstructor
public class BkPatientServiceImpl extends ServiceImpl<BkPatientMapper, BkPatient> implements BkPatientService {

    private final BkPatientMapper bkPatientMapper;

    /**
     * 分页查询[体检者]列表
     *
     * @param page  分页参数
     * @param param BkPatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BkPatient> getList(PageParam<BkPatient> page, BkPatientParam param) {
        return bkPatientMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BkPatient getInfoById(String id) {
        return bkPatientMapper.getInfoById(id);
    }

}

