package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemsAndFzx;
import com.center.medical.data.dao.ItemsAndFzxMapper;
import com.center.medical.data.service.ItemsAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 11:02:14
 */
@Slf4j
@Service("itemsAndFzxService")
@RequiredArgsConstructor
public class ItemsAndFzxServiceImpl extends ServiceImpl<ItemsAndFzxMapper, ItemsAndFzx> implements ItemsAndFzxService {

    private final ItemsAndFzxMapper itemsAndFzxMapper;

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ItemsAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ItemsAndFzx> getList(PageParam<ItemsAndFzx> page, ItemsAndFzx param) {
        return itemsAndFzxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id ItemsAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public ItemsAndFzx getInfoById(String id) {
        return itemsAndFzxMapper.getInfoById(id);
    }

}

