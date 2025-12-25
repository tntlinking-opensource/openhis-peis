package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ElectroAudiometerMapper;
import com.center.medical.datamove.oracle.bean.model.ElectroAudiometer;
import com.center.medical.datamove.oracle.service.ElectroAudiometerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS电测听(ElectroAudiometer)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:35
 */
@Slf4j
@Service("electroAudiometerService")
@RequiredArgsConstructor
public class ElectroAudiometerServiceImpl extends ServiceImpl<ElectroAudiometerMapper, ElectroAudiometer> implements ElectroAudiometerService {

    private final ElectroAudiometerMapper electroAudiometerMapper;

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param ElectroAudiometer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ElectroAudiometer> getPage(PageParam<ElectroAudiometer> page, ElectroAudiometer param) {
        return electroAudiometerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ElectroAudiometer getInfoById(String id) {
        return electroAudiometerMapper.getInfoById(id);
    }

}


