package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ReceiptTypeMapper;
import com.center.medical.bean.model.ReceiptType;
import com.center.medical.service.ReceiptTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 发票类型(ReceiptType)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:25
 */
@Slf4j
@Service("receiptTypeService")
@RequiredArgsConstructor
public class ReceiptTypeServiceImpl extends ServiceImpl<ReceiptTypeMapper, ReceiptType> implements ReceiptTypeService {

    private final ReceiptTypeMapper receiptTypeMapper;

    /**
     * 分页查询[发票类型]列表
     *
     * @param page  分页参数
     * @param param ReceiptType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReceiptType> getList(PageParam<ReceiptType> page, ReceiptType param) {
        return receiptTypeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ReceiptType getInfoById(String id) {
        return receiptTypeMapper.getInfoById(id);
    }

    ;

}

