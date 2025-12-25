package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrElectroAudiometer;
import com.center.medical.olddata.dao.OrElectroAudiometerMapper;
import com.center.medical.olddata.service.OrElectroAudiometerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS电测听(ElectroAudiometer)服务实现类
 *
 * @author ay
 * @since 2024-06-05 15:45:03
 */
@Slf4j
@Service("orElectroAudiometerService")
@RequiredArgsConstructor
public class OrElectroAudiometerServiceImpl extends ServiceImpl<OrElectroAudiometerMapper, OrElectroAudiometer> implements OrElectroAudiometerService {

    private final OrElectroAudiometerMapper orElectroAudiometerMapper;

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param ElectroAudiometer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrElectroAudiometer> getPage(PageParam<OrElectroAudiometer> page, OrElectroAudiometer param) {
        return orElectroAudiometerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrElectroAudiometer getInfoById(String id) {
        return orElectroAudiometerMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrElectroAudiometer> getListByPatientCode(String patientCode) {
        return orElectroAudiometerMapper.selectList(new LambdaQueryWrapper<OrElectroAudiometer>()
                .eq(OrElectroAudiometer::getPatientcode,patientCode));
    }
}

