package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrCreatemeal;
import com.center.medical.olddata.dao.OrCreatemealMapper;
import com.center.medical.olddata.service.OrCreatemealService;
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
public class OrCreatemealServiceImpl extends ServiceImpl<OrCreatemealMapper, OrCreatemeal> implements OrCreatemealService {

    private final OrCreatemealMapper createmealMapper;

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param Createmeal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrCreatemeal> getPage(PageParam<OrCreatemeal> page, OrCreatemeal param) {
        return createmealMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrCreatemeal getInfoById(String id) {
        return createmealMapper.getInfoById(id);
    }

}


