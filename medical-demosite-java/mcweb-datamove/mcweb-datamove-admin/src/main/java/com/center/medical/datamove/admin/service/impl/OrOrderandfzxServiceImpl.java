package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Orderandfzx;
import com.center.medical.datamove.admin.dao.OrOrderandfzxMapper;
import com.center.medical.datamove.admin.service.OrOrderandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单与分中心关联表(Orderandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:10:28
 */
@Slf4j
@Service("orOrderandfzxService")
@RequiredArgsConstructor
public class OrOrderandfzxServiceImpl extends ServiceImpl<OrOrderandfzxMapper, Orderandfzx> implements OrOrderandfzxService {

    private final OrOrderandfzxMapper orOrderandfzxMapper;

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Orderandfzx> getPage(PageParam<Orderandfzx> page, Orderandfzx param) {
        return orOrderandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Orderandfzx getInfoById(String id) {
        return orOrderandfzxMapper.getInfoById(id);
    }


    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Orderandfzx> getByDdid(String id) {
        return orOrderandfzxMapper.selectList(new LambdaQueryWrapper<Orderandfzx>().eq(Orderandfzx::getDdid, id));
    }
}


