package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Createcombo;
import com.center.medical.datamove.admin.dao.OrCreatecomboMapper;
import com.center.medical.datamove.admin.service.OrCreatecomboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 套餐表(Createcombo)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:56:43
 */
@Slf4j
@Service("orCreatecomboService")
@RequiredArgsConstructor
public class OrCreatecomboServiceImpl extends ServiceImpl<OrCreatecomboMapper, Createcombo> implements OrCreatecomboService {

    private final OrCreatecomboMapper orCreatecomboMapper;

    /**
     * 分页查询[套餐表]列表
     *
     * @param page  分页参数
     * @param param Createcombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createcombo> getPage(PageParam<Createcombo> page, Createcombo param) {
        return orCreatecomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Createcombo getInfoById(String id) {
        return orCreatecomboMapper.getInfoById(id);
    }

}


