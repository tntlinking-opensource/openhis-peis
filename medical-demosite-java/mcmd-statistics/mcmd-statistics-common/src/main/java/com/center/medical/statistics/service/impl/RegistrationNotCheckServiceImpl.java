package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.RegistrationNotCheckParam;
import com.center.medical.statistics.bean.vo.RegistrationNotCheckVo;
import com.center.medical.statistics.dao.RegistrationNotCheckMapper;
import com.center.medical.statistics.service.RegistrationNotCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登记未检客户统计(Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-12-18 15:52:58
 */
@Slf4j
@Service("registrationNotCheckService")
@RequiredArgsConstructor
public class RegistrationNotCheckServiceImpl extends ServiceImpl<RegistrationNotCheckMapper, Peispatient> implements RegistrationNotCheckService {

    private final RegistrationNotCheckMapper registrationNotCheckMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<RegistrationNotCheckVo> getPage(PageParam<RegistrationNotCheckVo> page, RegistrationNotCheckParam param) {
        return registrationNotCheckMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return registrationNotCheckMapper.getInfoById(id);
    }

    /**
     * 导出登记未检客户统计
     * @param param
     * @return
     */
    @Override
    public List<RegistrationNotCheckVo> export(RegistrationNotCheckParam param) {
        return registrationNotCheckMapper.export(param);
    }
}

