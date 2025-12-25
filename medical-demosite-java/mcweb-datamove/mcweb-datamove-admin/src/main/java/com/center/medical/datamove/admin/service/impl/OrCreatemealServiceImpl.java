package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Createmeal;
import com.center.medical.datamove.admin.dao.OrCreatemealMapper;
import com.center.medical.datamove.admin.service.OrCreatemealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 普通套餐表(Createmeal)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:56:07
 */
@Slf4j
@Service("orCreatemealService")
@RequiredArgsConstructor
public class OrCreatemealServiceImpl extends ServiceImpl<OrCreatemealMapper, Createmeal> implements OrCreatemealService {

    private final OrCreatemealMapper orCreatemealMapper;

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param Createmeal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createmeal> getPage(PageParam<Createmeal> page, Createmeal param) {
        return orCreatemealMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Createmeal getInfoById(String id) {
        return orCreatemealMapper.getInfoById(id);
    }

}


