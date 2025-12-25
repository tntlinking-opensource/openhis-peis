package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrDescribe;
import com.center.medical.olddata.dao.OrDescribeMapper;
import com.center.medical.olddata.service.OrDescribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS科室描述、检查结果表(Describe)服务实现类
 *
 * @author ay
 * @since 2024-06-05 15:35:57
 */
@Slf4j
@Service("orDescribeService")
@RequiredArgsConstructor
public class OrDescribeServiceImpl extends ServiceImpl<OrDescribeMapper, OrDescribe> implements OrDescribeService {

    private final OrDescribeMapper orDescribeMapper;

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param Describe查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrDescribe> getPage(PageParam<OrDescribe> page, OrDescribe param) {
        return orDescribeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrDescribe getInfoById(String id) {
        return orDescribeMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrDescribe> getListByPatientCode(String patientCode) {
        return orDescribeMapper.selectList(new LambdaQueryWrapper<OrDescribe>()
                .eq(OrDescribe::getPatientcode,patientCode));
    }
}

