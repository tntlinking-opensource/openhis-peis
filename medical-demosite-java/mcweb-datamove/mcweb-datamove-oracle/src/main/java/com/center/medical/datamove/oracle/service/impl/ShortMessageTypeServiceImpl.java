package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ShortMessageTypeMapper;
import com.center.medical.datamove.oracle.bean.model.ShortMessageType;
import com.center.medical.datamove.oracle.service.ShortMessageTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC短信信息分类表(ShortMessageType)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:59
 */
@Slf4j
@Service("shortMessageTypeService")
@RequiredArgsConstructor
public class ShortMessageTypeServiceImpl extends ServiceImpl<ShortMessageTypeMapper, ShortMessageType> implements ShortMessageTypeService {

    private final ShortMessageTypeMapper shortMessageTypeMapper;

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param ShortMessageType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ShortMessageType> getPage(PageParam<ShortMessageType> page, ShortMessageType param) {
        return shortMessageTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ShortMessageType getInfoById(String id) {
        return shortMessageTypeMapper.getInfoById(id);
    }

    ;

}


