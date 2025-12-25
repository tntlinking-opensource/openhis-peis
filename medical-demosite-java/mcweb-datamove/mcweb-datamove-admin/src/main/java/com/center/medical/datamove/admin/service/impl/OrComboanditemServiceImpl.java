package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Comboanditem;
import com.center.medical.datamove.admin.dao.OrComboanditemMapper;
import com.center.medical.datamove.admin.service.OrComboanditemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:07:23
 */
@Slf4j
@Service("orComboanditemService")
@RequiredArgsConstructor
public class OrComboanditemServiceImpl extends ServiceImpl<OrComboanditemMapper, Comboanditem> implements OrComboanditemService {

    private final OrComboanditemMapper orComboanditemMapper;

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Comboanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Comboanditem> getPage(PageParam<Comboanditem> page, Comboanditem param) {
        return orComboanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Comboanditem getInfoById(String id) {
        return orComboanditemMapper.getInfoById(id);
    }


    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Comboanditem> getByTcid(String tcid) {
        return orComboanditemMapper.selectList(new LambdaQueryWrapper<Comboanditem>().eq(Comboanditem::getTcid, tcid));
    }
}


