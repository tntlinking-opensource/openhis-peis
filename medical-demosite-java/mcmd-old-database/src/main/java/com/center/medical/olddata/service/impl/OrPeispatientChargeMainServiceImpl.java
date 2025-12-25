package com.center.medical.olddata.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientChargeMain;
import com.center.medical.olddata.dao.OrPeispatientChargeMainMapper;
import com.center.medical.olddata.service.OrPeispatientChargeMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (PeispatientChargeMain)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:55:02
 */
@Slf4j
@Service("orPeispatientChargeMainService")
@RequiredArgsConstructor
public class OrPeispatientChargeMainServiceImpl extends ServiceImpl<OrPeispatientChargeMainMapper, OrPeispatientChargeMain> implements OrPeispatientChargeMainService {

    private final OrPeispatientChargeMainMapper peispatientChargeMainMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeispatientChargeMain> getPage(PageParam<OrPeispatientChargeMain> page, OrPeispatientChargeMain param) {
        return peispatientChargeMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrPeispatientChargeMain getInfoById(String id) {
        return peispatientChargeMainMapper.getInfoById(id);
    }


    /**
     * 通过体检号获取数据
     *
     * @param patientcode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrPeispatientChargeMain getByPatientCode(String patientcode) {
        List<OrPeispatientChargeMain> list = peispatientChargeMainMapper.selectList(new LambdaQueryWrapper<OrPeispatientChargeMain>()
                .eq(OrPeispatientChargeMain::getPatientcode, patientcode)
                .orderByDesc(OrPeispatientChargeMain::getCreatedate)
        );
        if (CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }

    }
}


