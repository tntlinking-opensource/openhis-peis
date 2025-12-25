package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ItemsBarcodeClassMapper;
import com.center.medical.datamove.oracle.bean.model.ItemsBarcodeClass;
import com.center.medical.datamove.oracle.service.ItemsBarcodeClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 收费项目条码打印分类(ItemsBarcodeClass)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:23
 */
@Slf4j
@Service("itemsBarcodeClassService")
@RequiredArgsConstructor
public class ItemsBarcodeClassServiceImpl extends ServiceImpl<ItemsBarcodeClassMapper, ItemsBarcodeClass> implements ItemsBarcodeClassService {

    private final ItemsBarcodeClassMapper itemsBarcodeClassMapper;

    /**
     * 分页查询[收费项目条码打印分类]列表
     *
     * @param page  分页参数
     * @param param ItemsBarcodeClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ItemsBarcodeClass> getPage(PageParam<ItemsBarcodeClass> page, ItemsBarcodeClass param) {
        return itemsBarcodeClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ItemsBarcodeClass getInfoById(String id) {
        return itemsBarcodeClassMapper.getInfoById(id);
    }

}


