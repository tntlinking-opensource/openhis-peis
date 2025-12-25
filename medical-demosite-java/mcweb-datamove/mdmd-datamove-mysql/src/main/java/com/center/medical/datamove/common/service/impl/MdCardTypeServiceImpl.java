package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCardTypeMapper;
import com.center.medical.datamove.common.bean.model.MdCardType;
import com.center.medical.datamove.common.service.MdCardTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 卡类型(MdCardType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
@Slf4j
@Service("mdCardTypeService")
@RequiredArgsConstructor
public class MdCardTypeServiceImpl extends ServiceImpl<MdCardTypeMapper, MdCardType> implements MdCardTypeService {

    private final MdCardTypeMapper mdCardTypeMapper;

    /**
     * 分页查询[卡类型]列表
     *
     * @param page  分页参数
     * @param param MdCardType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCardType> getPage(PageParam<MdCardType> page, MdCardType param) {
        return mdCardTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCardType getInfoById(String id) {
        return mdCardTypeMapper.getInfoById(id);
    }

}


