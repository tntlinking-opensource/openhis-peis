package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdElectroAudiometer;
import com.center.medical.olddata.dao.MdElectroAudiometerMapper;
import com.center.medical.olddata.service.MdElectroAudiometerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * KS电测听(MdElectroAudiometer)服务实现类
 *
 * @author ay
 * @since 2024-06-05 16:03:15
 */
@Slf4j
@Service("mdElectroAudiometerService")
@RequiredArgsConstructor
public class MdElectroAudiometerServiceImpl extends ServiceImpl<MdElectroAudiometerMapper, MdElectroAudiometer> implements MdElectroAudiometerService {

    private final MdElectroAudiometerMapper mdElectroAudiometerMapper;

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param MdElectroAudiometer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdElectroAudiometer> getPage(PageParam<MdElectroAudiometer> page, MdElectroAudiometer param) {
        return mdElectroAudiometerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdElectroAudiometer getInfoById(String id) {
        return mdElectroAudiometerMapper.getInfoById(id);
    }

}

