package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsBasexamltem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PacsBasexamltemsMapper;
import com.center.medical.service.PacsBasexamltemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * PACS-基础检查项(PacsBasexamltem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:55
 */
@Slf4j
@Service("pacsBasexamltemService")
@RequiredArgsConstructor
public class PacsBasexamltemServiceImpl extends ServiceImpl<PacsBasexamltemsMapper, PacsBasexamltem> implements PacsBasexamltemService {

    private final PacsBasexamltemsMapper pacsBasexamltemsMapper;

    /**
     * 分页查询[PACS-基础检查项]列表
     *
     * @param page  分页参数
     * @param param PacsBasexamltem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasexamltem> getList(PageParam<PacsBasexamltem> page, PacsBasexamltem param) {
        return pacsBasexamltemsMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsBasexamltem getInfoById(String id) {
        return pacsBasexamltemsMapper.getInfoById(id);
    }

}

