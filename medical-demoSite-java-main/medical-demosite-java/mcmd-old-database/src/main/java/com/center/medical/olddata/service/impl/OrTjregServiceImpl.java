package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrTjreg;
import com.center.medical.olddata.dao.OrTjregMapper;
import com.center.medical.olddata.service.OrTjregService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS一般检查(Tjreg)服务实现类
 *
 * @author ay
 * @since 2024-06-05 15:37:36
 */
@Slf4j
@Service("OrTjregService")
@RequiredArgsConstructor
public class OrTjregServiceImpl extends ServiceImpl<OrTjregMapper, OrTjreg> implements OrTjregService {

    private final OrTjregMapper orTjregMapper;

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param Tjreg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrTjreg> getPage(PageParam<OrTjreg> page, OrTjreg param) {
        return orTjregMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrTjreg getInfoById(String id) {
        return orTjregMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrTjreg> getListByPatientCode(String patientCode) {
        return orTjregMapper.selectList(new LambdaQueryWrapper<OrTjreg>()
                .eq(OrTjreg::getPatientcode,patientCode));
    }
}

