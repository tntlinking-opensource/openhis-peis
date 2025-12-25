package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.PeispatientChargeMain;
import com.center.medical.datamove.oracle.dao.PeispatientChargeMainMapper;
import com.center.medical.datamove.oracle.service.PeispatientChargeMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * (PeispatientChargeMain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:14
 */
@Slf4j
@Service("peispatientChargeMainService")
@RequiredArgsConstructor
public class PeispatientChargeMainServiceImpl extends ServiceImpl<PeispatientChargeMainMapper, PeispatientChargeMain> implements PeispatientChargeMainService {

    private final PeispatientChargeMainMapper peispatientChargeMainMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientChargeMain> getPage(PageParam<PeispatientChargeMain> page, PeispatientChargeMain param) {
        return peispatientChargeMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientChargeMain getInfoById(String id) {
        return peispatientChargeMainMapper.getInfoById(id);
    }


    /**
     * 通过体检号查询
     *
     * @param patientcode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public PeispatientChargeMain getByPatientCode(String patientcode) {
        PeispatientChargeMain peispatientChargeMain = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                .eq(PeispatientChargeMain::getPatientcode, patientcode));
        return peispatientChargeMain;
    }
}


