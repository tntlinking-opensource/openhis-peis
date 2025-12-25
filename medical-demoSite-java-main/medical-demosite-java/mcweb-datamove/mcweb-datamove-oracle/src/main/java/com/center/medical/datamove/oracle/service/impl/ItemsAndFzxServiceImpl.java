package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ItemsAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.ItemsAndFzx;
import com.center.medical.datamove.oracle.service.ItemsAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:22
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
    public IPage<ItemsAndFzx> getPage(PageParam<ItemsAndFzx> page, ItemsAndFzx param) {
        return itemsAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ItemsAndFzx getInfoById(String id) {
        return itemsAndFzxMapper.getInfoById(id);
    }

}


