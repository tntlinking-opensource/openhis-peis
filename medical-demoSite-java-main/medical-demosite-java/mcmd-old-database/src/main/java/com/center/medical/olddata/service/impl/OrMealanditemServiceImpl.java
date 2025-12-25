package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrMealanditem;
import com.center.medical.olddata.dao.OrMealanditemMapper;
import com.center.medical.olddata.service.OrMealanditemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通套餐与收费项目关联表(Mealanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:25:30
 */
@Slf4j
@Service("orMealanditemService")
@RequiredArgsConstructor
public class OrMealanditemServiceImpl extends ServiceImpl<OrMealanditemMapper, OrMealanditem> implements OrMealanditemService {

    private final OrMealanditemMapper mealanditemMapper;

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Mealanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrMealanditem> getPage(PageParam<OrMealanditem> page, OrMealanditem param) {
        return mealanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrMealanditem getInfoById(String id) {
        return mealanditemMapper.getInfoById(id);
    }


    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrMealanditem> getByTcid(String tcid) {
        return mealanditemMapper.selectList(new LambdaQueryWrapper<OrMealanditem>().eq(OrMealanditem::getTcid, tcid));
    }
}


