package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientcharge;
import com.center.medical.olddata.dao.OrPeispatientchargeMapper;
import com.center.medical.olddata.service.OrPeispatientchargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者缴费表(Peispatientcharge)服务实现类
 *
 * @author ay
 * @since 2023-11-10 14:59:30
 */
@Slf4j
@Service("orPeispatientchargeService")
@RequiredArgsConstructor
public class OrPeispatientchargeServiceImpl extends ServiceImpl<OrPeispatientchargeMapper, OrPeispatientcharge> implements OrPeispatientchargeService {

    private final OrPeispatientchargeMapper orPeispatientchargeMapper;

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param Peispatientcharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeispatientcharge> getPage(PageParam<OrPeispatientcharge> page, OrPeispatientcharge param) {
        return orPeispatientchargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrPeispatientcharge getInfoById(String id) {
        return orPeispatientchargeMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param oldPatientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatientcharge> getByPatientCode(String oldPatientCode) {
        return orPeispatientchargeMapper.getByPatientCode(oldPatientCode);
    }
}

