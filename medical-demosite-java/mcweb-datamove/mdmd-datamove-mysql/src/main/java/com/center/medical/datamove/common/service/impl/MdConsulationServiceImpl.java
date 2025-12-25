package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdConsulationMapper;
import com.center.medical.datamove.common.bean.model.MdConsulation;
import com.center.medical.datamove.common.service.MdConsulationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 科室/总检咨询(MdConsulation)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
@Slf4j
@Service("mdConsulationService")
@RequiredArgsConstructor
public class MdConsulationServiceImpl extends ServiceImpl<MdConsulationMapper, MdConsulation> implements MdConsulationService {

    private final MdConsulationMapper mdConsulationMapper;

    /**
     * 分页查询[科室/总检咨询]列表
     *
     * @param page  分页参数
     * @param param MdConsulation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdConsulation> getPage(PageParam<MdConsulation> page, MdConsulation param) {
        return mdConsulationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdConsulation getInfoById(String id) {
        return mdConsulationMapper.getInfoById(id);
    }

}


