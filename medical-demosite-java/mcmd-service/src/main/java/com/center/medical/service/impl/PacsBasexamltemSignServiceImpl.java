package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PacsBasexamltemSignMapper;
import com.center.medical.bean.model.PacsBasexamltemSign;
import com.center.medical.service.PacsBasexamltemSignService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-体征词(PacsBasexamltemSign)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:49
 */
@Slf4j
@Service("pacsBasexamltemSignService")
@RequiredArgsConstructor
public class PacsBasexamltemSignServiceImpl extends ServiceImpl<PacsBasexamltemSignMapper, PacsBasexamltemSign> implements PacsBasexamltemSignService {

    private final PacsBasexamltemSignMapper pacsBasexamltemSignMapper;

    /**
     * 分页查询[PACS-体征词]列表
     *
     * @param page  分页参数
     * @param param PacsBasexamltemSign查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasexamltemSign> getList(PageParam<PacsBasexamltemSign> page, PacsBasexamltemSign param) {
        return pacsBasexamltemSignMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsBasexamltemSign getInfoById(String id) {
        return pacsBasexamltemSignMapper.getInfoById(id);
    }

}

