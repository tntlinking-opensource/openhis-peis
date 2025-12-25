package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OutsideChargeItemMapper;
import com.center.medical.datamove.oracle.bean.model.OutsideChargeItem;
import com.center.medical.datamove.oracle.service.OutsideChargeItemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送项目表(OutsideChargeItem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:30
 */
@Slf4j
@Service("outsideChargeItemService")
@RequiredArgsConstructor
public class OutsideChargeItemServiceImpl extends ServiceImpl<OutsideChargeItemMapper, OutsideChargeItem> implements OutsideChargeItemService {

    private final OutsideChargeItemMapper outsideChargeItemMapper;

    /**
     * 分页查询[KS外送项目表]列表
     *
     * @param page  分页参数
     * @param param OutsideChargeItem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OutsideChargeItem> getPage(PageParam<OutsideChargeItem> page, OutsideChargeItem param) {
        return outsideChargeItemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OutsideChargeItem getInfoById(String id) {
        return outsideChargeItemMapper.getInfoById(id);
    }

}


