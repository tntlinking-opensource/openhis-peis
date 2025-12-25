package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdShortMessageTypeMapper;
import com.center.medical.datamove.common.bean.model.MdShortMessageType;
import com.center.medical.datamove.common.service.MdShortMessageTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC短信信息分类表(MdShortMessageType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:41
 */
@Slf4j
@Service("mdShortMessageTypeService")
@RequiredArgsConstructor
public class MdShortMessageTypeServiceImpl extends ServiceImpl<MdShortMessageTypeMapper, MdShortMessageType> implements MdShortMessageTypeService {

    private final MdShortMessageTypeMapper mdShortMessageTypeMapper;

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param MdShortMessageType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdShortMessageType> getPage(PageParam<MdShortMessageType> page, MdShortMessageType param) {
        return mdShortMessageTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdShortMessageType getInfoById(String id) {
        return mdShortMessageTypeMapper.getInfoById(id);
    }

}


