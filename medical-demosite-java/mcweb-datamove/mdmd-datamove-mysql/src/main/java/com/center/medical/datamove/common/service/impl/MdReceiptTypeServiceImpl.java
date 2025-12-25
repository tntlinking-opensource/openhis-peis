package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReceiptTypeMapper;
import com.center.medical.datamove.common.bean.model.MdReceiptType;
import com.center.medical.datamove.common.service.MdReceiptTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 发票类型(MdReceiptType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
@Slf4j
@Service("mdReceiptTypeService")
@RequiredArgsConstructor
public class MdReceiptTypeServiceImpl extends ServiceImpl<MdReceiptTypeMapper, MdReceiptType> implements MdReceiptTypeService {

    private final MdReceiptTypeMapper mdReceiptTypeMapper;

    /**
     * 分页查询[发票类型]列表
     *
     * @param page  分页参数
     * @param param MdReceiptType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReceiptType> getPage(PageParam<MdReceiptType> page, MdReceiptType param) {
        return mdReceiptTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReceiptType getInfoById(String id) {
        return mdReceiptTypeMapper.getInfoById(id);
    }

}


