package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrItems;
import com.center.medical.olddata.dao.OrItemsMapper;
import com.center.medical.olddata.service.OrItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC收费项目表(Items)服务实现类
 *
 * @author ay
 * @since 2024-07-13 14:27:28
 */
@Slf4j
@Service("orItemsService")
@RequiredArgsConstructor
public class OrItemsServiceImpl extends ServiceImpl<OrItemsMapper, OrItems> implements OrItemsService {

    private final OrItemsMapper orItemsMapper;

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param Items查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrItems> getPage(PageParam<OrItems> page, OrItems param) {
        return orItemsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrItems getInfoById(String id) {
        return orItemsMapper.getInfoById(id);
    }

}

