package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionTotal;
import com.center.medical.olddata.dao.OrSectionTotalMapper;
import com.center.medical.olddata.service.OrSectionTotalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ZJ总检主表(SectionTotal)服务实现类
 *
 * @author ay
 * @since 2024-06-05 14:39:41
 */
@Slf4j
@Service("orSectionTotalService")
@RequiredArgsConstructor
public class OrSectionTotalServiceImpl extends ServiceImpl<OrSectionTotalMapper, OrSectionTotal> implements OrSectionTotalService {

    private final OrSectionTotalMapper orSectionTotalMapper;

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param SectionTotal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrSectionTotal> getPage(PageParam<OrSectionTotal> page, OrSectionTotal param) {
        return orSectionTotalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrSectionTotal getInfoById(String id) {
        return orSectionTotalMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrSectionTotal> getListByPatientCode(String patientCode) {
        return orSectionTotalMapper.selectList(new LambdaQueryWrapper<OrSectionTotal>()
                .eq(OrSectionTotal::getPatientcode,patientCode));
    }
}

