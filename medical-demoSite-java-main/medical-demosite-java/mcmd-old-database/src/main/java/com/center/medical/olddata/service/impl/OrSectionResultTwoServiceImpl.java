package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionResultTwo;
import com.center.medical.olddata.dao.OrSectionResultTwoMapper;
import com.center.medical.olddata.service.OrSectionResultTwoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS科室检查结果表————结论词、小结(SectionResultTwo)服务实现类
 *
 * @author ay
 * @since 2024-06-05 14:39:41
 */
@Slf4j
@Service("orSectionResultTwoService")
@RequiredArgsConstructor
public class OrSectionResultTwoServiceImpl extends ServiceImpl<OrSectionResultTwoMapper, OrSectionResultTwo> implements OrSectionResultTwoService {

    private final OrSectionResultTwoMapper orSectionResultTwoMapper;

    /**
     * 分页查询[KS科室检查结果表————结论词、小结]列表
     *
     * @param page  分页参数
     * @param param SectionResultTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrSectionResultTwo> getPage(PageParam<OrSectionResultTwo> page, OrSectionResultTwo param) {
        return orSectionResultTwoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrSectionResultTwo getInfoById(String id) {
        return orSectionResultTwoMapper.getInfoById(id);
    }

    /**
     * 通过体检号查新
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrSectionResultTwo> getListByPatientCode(String patientCode) {
        return orSectionResultTwoMapper.selectList(new LambdaQueryWrapper<OrSectionResultTwo>()
                .eq(OrSectionResultTwo::getPatientcode,patientCode));
    }
}

