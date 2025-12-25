package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Mealandfzx;
import com.center.medical.datamove.admin.dao.OrMealandfzxMapper;
import com.center.medical.datamove.admin.service.OrMealandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通套餐与分中心关联表(Mealandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:25:31
 */
@Slf4j
@Service("orMealandfzxService")
@RequiredArgsConstructor
public class OrMealandfzxServiceImpl extends ServiceImpl<OrMealandfzxMapper, Mealandfzx> implements OrMealandfzxService {

    private final OrMealandfzxMapper orMealandfzxMapper;

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Mealandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Mealandfzx> getPage(PageParam<Mealandfzx> page, Mealandfzx param) {
        return orMealandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Mealandfzx getInfoById(String id) {
        return orMealandfzxMapper.getInfoById(id);
    }

    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Mealandfzx> getByTcid(String tcid) {
        return orMealandfzxMapper.selectList(new LambdaQueryWrapper<Mealandfzx>().eq(Mealandfzx::getTcid, tcid));
    }
}


