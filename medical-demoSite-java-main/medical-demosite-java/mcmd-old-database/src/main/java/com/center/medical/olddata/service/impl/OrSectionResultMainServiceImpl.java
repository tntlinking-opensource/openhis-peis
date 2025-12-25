package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionResultMain;
import com.center.medical.olddata.dao.OrSectionResultMainMapper;
import com.center.medical.olddata.service.OrSectionResultMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS科室检查结果主表(SectionResultMain)服务实现类
 *
 * @author ay
 * @since 2024-06-05 14:39:40
 */
@Slf4j
@Service("orSectionResultMainService")
@RequiredArgsConstructor
public class OrSectionResultMainServiceImpl extends ServiceImpl<OrSectionResultMainMapper, OrSectionResultMain> implements OrSectionResultMainService {

    private final OrSectionResultMainMapper orSectionResultMainMapper;

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param SectionResultMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrSectionResultMain> getPage(PageParam<OrSectionResultMain> page, OrSectionResultMain param) {
        return orSectionResultMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrSectionResultMain getInfoById(String id) {
        return orSectionResultMainMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrSectionResultMain> getListByPatientCode(String patientCode) {
        return orSectionResultMainMapper.selectList(new LambdaQueryWrapper<OrSectionResultMain>()
                .eq(OrSectionResultMain::getPatientcode,patientCode));
    }
}

