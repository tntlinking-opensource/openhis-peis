package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ConsulationMapper;
import com.center.medical.datamove.oracle.bean.model.Consulation;
import com.center.medical.datamove.oracle.service.ConsulationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Consulation)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:48
 */
@Slf4j
@Service("consulationService")
@RequiredArgsConstructor
public class ConsulationServiceImpl extends ServiceImpl<ConsulationMapper, Consulation> implements ConsulationService {

    private final ConsulationMapper consulationMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Consulation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Consulation> getPage(PageParam<Consulation> page, Consulation param) {
        return consulationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Consulation getInfoById(String id) {
        return consulationMapper.getInfoById(id);
    }

}


