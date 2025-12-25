package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrLung;
import com.center.medical.olddata.dao.OrLungMapper;
import com.center.medical.olddata.service.OrLungService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS肺功能(Lung)服务实现类
 *
 * @author ay
 * @since 2024-06-05 15:44:03
 */
@Slf4j
@Service("orLungService")
@RequiredArgsConstructor
public class OrOrLungServiceImpl extends ServiceImpl<OrLungMapper, OrLung> implements OrLungService {

    private final OrLungMapper orLungMapper;

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param Lung查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrLung> getPage(PageParam<OrLung> page, OrLung param) {
        return orLungMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrLung getInfoById(String id) {
        return orLungMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrLung> getListByPatientCode(String patientCode) {
        return orLungMapper.selectList(new LambdaQueryWrapper<OrLung>()
                .eq(OrLung::getPatientcode,patientCode));
    }
}

