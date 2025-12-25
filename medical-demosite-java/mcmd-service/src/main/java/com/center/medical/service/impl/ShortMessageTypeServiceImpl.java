package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ShortMessageTypeMapper;
import com.center.medical.bean.model.ShortMessageType;
import com.center.medical.service.ShortMessageTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC短信信息分类表(ShortMessageType)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:55
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
    public IPage<ShortMessageType> getList(PageParam<ShortMessageType> page, ShortMessageType param) {
        return shortMessageTypeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ShortMessageType getInfoById(String id) {
        return shortMessageTypeMapper.getInfoById(id);
    }

    ;

}

