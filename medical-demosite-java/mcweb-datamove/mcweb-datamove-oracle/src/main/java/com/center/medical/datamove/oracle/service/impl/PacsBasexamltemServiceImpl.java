package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsBasexamltemMapper;
import com.center.medical.datamove.oracle.bean.model.PacsBasexamltem;
import com.center.medical.datamove.oracle.service.PacsBasexamltemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsBasexamltem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:41
 */
@Slf4j
@Service("pacsBasexamltemService")
@RequiredArgsConstructor
public class PacsBasexamltemServiceImpl extends ServiceImpl<PacsBasexamltemMapper, PacsBasexamltem> implements PacsBasexamltemService {

    private final PacsBasexamltemMapper pacsBasexamltemMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsBasexamltem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasexamltem> getPage(PageParam<PacsBasexamltem> page, PacsBasexamltem param) {
        return pacsBasexamltemMapper.getPage(page, param);
    }


}


