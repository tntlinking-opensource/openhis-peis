package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Comboandfzx;
import com.center.medical.datamove.admin.dao.OrComboandfzxMapper;
import com.center.medical.datamove.admin.service.OrComboandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 最小套餐与分中心关联表(Comboandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:07:21
 */
@Slf4j
@Service("orComboandfzxService")
@RequiredArgsConstructor
public class OrComboandfzxServiceImpl extends ServiceImpl<OrComboandfzxMapper, Comboandfzx> implements OrComboandfzxService {

    private final OrComboandfzxMapper orComboandfzxMapper;

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Comboandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Comboandfzx> getPage(PageParam<Comboandfzx> page, Comboandfzx param) {
        return orComboandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Comboandfzx getInfoById(String id) {
        return orComboandfzxMapper.getInfoById(id);
    }


    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Comboandfzx> getByTcid(String tcid) {
        return orComboandfzxMapper.selectList(new LambdaQueryWrapper<Comboandfzx>().eq(Comboandfzx::getTcid, tcid));
    }
}


