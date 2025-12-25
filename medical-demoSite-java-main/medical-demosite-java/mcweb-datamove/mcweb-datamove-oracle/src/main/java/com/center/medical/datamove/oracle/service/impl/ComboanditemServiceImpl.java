package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Comboanditem;
import com.center.medical.datamove.oracle.dao.ComboanditemMapper;
import com.center.medical.datamove.oracle.service.ComboanditemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:41
 */
@Slf4j
@Service("comboanditemsService")
@RequiredArgsConstructor
public class ComboanditemServiceImpl extends ServiceImpl<ComboanditemMapper, Comboanditem> implements ComboanditemService {

    private final ComboanditemMapper comboanditemMapper;

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Comboanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Comboanditem> getPage(PageParam<Comboanditem> page, Comboanditem param) {
        return comboanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Comboanditem getInfoById(String id) {
        return comboanditemMapper.getInfoById(id);
    }

}


