package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TbPatientMapper;
import com.center.medical.bean.model.TbPatient;
import com.center.medical.service.TbPatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步主表(TbPatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:18
 */
@Slf4j
@Service("tbPatientService")
@RequiredArgsConstructor
public class TbPatientServiceImpl extends ServiceImpl<TbPatientMapper, TbPatient> implements TbPatientService {

    private final TbPatientMapper tbPatientMapper;

    /**
     * 分页查询[同步主表]列表
     *
     * @param page  分页参数
     * @param param TbPatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TbPatient> getList(PageParam<TbPatient> page, TbPatient param) {
        return tbPatientMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TbPatient getInfoById(String id) {
        return tbPatientMapper.getInfoById(id);
    }

}

