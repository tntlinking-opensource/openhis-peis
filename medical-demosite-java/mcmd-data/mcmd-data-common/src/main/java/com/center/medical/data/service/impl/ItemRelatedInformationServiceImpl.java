package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemRelatedInformation;
import com.center.medical.data.dao.ItemRelatedInformationMapper;
import com.center.medical.data.service.ItemRelatedInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 第三方接口关联记录(ItemRelatedInformation)表服务实现类
 *
 * @author ay
 * @since 2023-04-07 17:20:14
 */
@Slf4j
@Service("itemRelatedInformationService")
@RequiredArgsConstructor
public class ItemRelatedInformationServiceImpl extends ServiceImpl<ItemRelatedInformationMapper, ItemRelatedInformation> implements ItemRelatedInformationService {

    private final ItemRelatedInformationMapper itemRelatedInformationMapper;

    /**
     * 分页查询[第三方接口关联记录]列表
     *
     * @param page  分页参数
     * @param param ItemRelatedInformation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ItemRelatedInformation> getList(PageParam<ItemRelatedInformation> page, ItemRelatedInformation param) {
        return itemRelatedInformationMapper.getList(page, param);
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

    ;

}

