package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsBasexamltemSignMapper;
import com.center.medical.datamove.oracle.bean.model.PacsBasexamltemSign;
import com.center.medical.datamove.oracle.service.PacsBasexamltemSignService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsBasexamltemSign)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:42
 */
@Slf4j
@Service("pacsBasexamltemSignService")
@RequiredArgsConstructor
public class PacsBasexamltemSignServiceImpl extends ServiceImpl<PacsBasexamltemSignMapper, PacsBasexamltemSign> implements PacsBasexamltemSignService {

    private final PacsBasexamltemSignMapper pacsBasexamltemSignMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsBasexamltemSign查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasexamltemSign> getPage(PageParam<PacsBasexamltemSign> page, PacsBasexamltemSign param) {
        return pacsBasexamltemSignMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsBasexamltemSign getInfoById(String id) {
        return pacsBasexamltemSignMapper.getInfoById(id);
    }

}


