package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BasexamltemMapper;
import com.center.medical.datamove.oracle.bean.model.Basexamltem;
import com.center.medical.datamove.oracle.service.BasexamltemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC检查项目表(Basexamltem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:44
 */
@Slf4j
@Service("basexamltemService")
@RequiredArgsConstructor
public class BasexamltemServiceImpl extends ServiceImpl<BasexamltemMapper, Basexamltem> implements BasexamltemService {

    private final BasexamltemMapper basexamltemMapper;

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param Basexamltem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Basexamltem> getPage(PageParam<Basexamltem> page, Basexamltem param) {
        return basexamltemMapper.getPage(page, param);
    }


}


