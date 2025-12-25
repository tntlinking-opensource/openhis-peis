package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Createorder;
import com.center.medical.datamove.admin.dao.OrCreateorderMapper;
import com.center.medical.datamove.admin.service.OrCreateorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单表(Createorder)服务实现类
 *
 * @author ay
 * @since 2023-07-25 18:20:59
 */

@Slf4j
@Service("orCreateorderService")
@RequiredArgsConstructor
@DataSource(value = DataSourceType.SLAVE)
public class OrCreateorderServiceImpl extends ServiceImpl<OrCreateorderMapper, Createorder> implements OrCreateorderService {

    private final OrCreateorderMapper orCreateorderMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createorder> getPage(PageParam<Createorder> page, Createorder param) {
        return orCreateorderMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Createorder getInfoById(String id) {
        return orCreateorderMapper.getInfoById(id);
    }


    /**
     * 根据订单号获取记录详情
     *
     * @param ddh 订单号
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Createorder getInfoByDdh(String ddh) {
        return orCreateorderMapper.getInfoByDdh(ddh);
    }
}


