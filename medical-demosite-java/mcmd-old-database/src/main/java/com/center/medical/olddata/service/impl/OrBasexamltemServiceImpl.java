package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrBasexamltem;
import com.center.medical.olddata.dao.OrBasexamltemMapper;
import com.center.medical.olddata.service.OrBasexamltemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC检查项目表(Basexamltem)服务实现类
 *
 * @author ay
 * @since 2024-07-13 14:27:48
 */
@Slf4j
@Service("orBasexamltemService")
@RequiredArgsConstructor
public class OrBasexamltemServiceImpl extends ServiceImpl<OrBasexamltemMapper, OrBasexamltem> implements OrBasexamltemService {

    private final OrBasexamltemMapper orBasexamltemMapper;

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param Basexamltem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrBasexamltem> getPage(PageParam<OrBasexamltem> page, OrBasexamltem param) {
        return orBasexamltemMapper.getPage(page, param);
    }



    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrBasexamltem getInfoById(String id) {
        return orBasexamltemMapper.getInfoById(id);
    }
}

