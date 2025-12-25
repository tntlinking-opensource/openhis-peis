package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemsBarcodeClass;
import com.center.medical.data.dao.ItemsBarcodeClassMapper;
import com.center.medical.data.service.ItemsBarcodeClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 收费项目条码打印分类(ItemsBarcodeClass)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-18 10:27:20
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

