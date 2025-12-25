package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ItemsMapper;
import com.center.medical.datamove.oracle.bean.model.Items;
import com.center.medical.datamove.oracle.service.ItemsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC收费项目表(Items)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:20
 */
@Slf4j
@Service("itemsService")
@RequiredArgsConstructor
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {

    private final ItemsMapper itemsMapper;

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param Items查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Items> getPage(PageParam<Items> page, Items param) {
        return itemsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Items getInfoById(String id) {
        return itemsMapper.getInfoById(id);
    }

}


