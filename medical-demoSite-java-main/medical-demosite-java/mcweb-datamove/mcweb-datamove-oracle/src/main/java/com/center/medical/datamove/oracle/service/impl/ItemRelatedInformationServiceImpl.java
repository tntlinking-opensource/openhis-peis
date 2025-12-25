package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ItemRelatedInformationMapper;
import com.center.medical.datamove.oracle.bean.model.ItemRelatedInformation;
import com.center.medical.datamove.oracle.service.ItemRelatedInformationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 第三方项目接口对接信息表(ItemRelatedInformation)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:19
 */
@Slf4j
@Service("itemRelatedInformationService")
@RequiredArgsConstructor
public class ItemRelatedInformationServiceImpl extends ServiceImpl<ItemRelatedInformationMapper, ItemRelatedInformation> implements ItemRelatedInformationService {

    private final ItemRelatedInformationMapper itemRelatedInformationMapper;

    /**
     * 分页查询[第三方项目接口对接信息表]列表
     *
     * @param page  分页参数
     * @param param ItemRelatedInformation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ItemRelatedInformation> getPage(PageParam<ItemRelatedInformation> page, ItemRelatedInformation param) {
        return itemRelatedInformationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ItemRelatedInformation getInfoById(String id) {
        return itemRelatedInformationMapper.getInfoById(id);
    }

}


