package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboanditem;
import com.center.medical.sellcrm.bean.vo.ComboAndItemVo;
import com.center.medical.sellcrm.bean.vo.GetItemsVo;
import com.center.medical.sellcrm.dao.ComboanditemMapper;
import com.center.medical.sellcrm.service.ComboanditemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
@Slf4j
@Service("comboanditemService")
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
    public IPage<Comboanditem> getList(PageParam<Comboanditem> page, Comboanditem param) {
        return comboanditemMapper.getPageList(page, param);
    }

    /**
     * 获取和套餐关联的收费项目
     *
     * @param page    分页参数
     * @param comboId 套餐id
     * @return 列表数据
     */
    @Override
    public IPage<ComboAndItemVo> getListByComboId(PageParam<Comboanditem> page, String comboId) {
        return comboanditemMapper.getListByComboId(page, comboId);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Comboanditem getInfoById(String id) {
        return comboanditemMapper.getInfoById(id);
    }


    /**
     * 获取收费项目
     *
     * @param tcId
     * @return
     */
    @Override
    public List<GetItemsVo> getItems(String tcId) {
        return comboanditemMapper.getItems(tcId);
    }
}

