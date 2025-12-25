package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.PeispatientChargeMain;
import com.center.medical.datamove.admin.dao.OrPeispatientChargeMainMapper;
import com.center.medical.datamove.admin.service.OrPeispatientChargeMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * (PeispatientChargeMain)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:55:02
 */
@Slf4j
@Service("orPeispatientChargeMainService")
@RequiredArgsConstructor
public class OrPeispatientChargeMainServiceImpl extends ServiceImpl<OrPeispatientChargeMainMapper, PeispatientChargeMain> implements OrPeispatientChargeMainService {

    private final OrPeispatientChargeMainMapper orPeispatientChargeMainMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientChargeMain> getPage(PageParam<PeispatientChargeMain> page, PeispatientChargeMain param) {
        return orPeispatientChargeMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientChargeMain getInfoById(String id) {
        return orPeispatientChargeMainMapper.getInfoById(id);
    }


    /**
     * 通过体检号获取数据
     *
     * @param patientcode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public PeispatientChargeMain getByPatientCode(String patientcode) {
        return orPeispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, patientcode));
    }
}


