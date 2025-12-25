package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDrinkingTypeMapper;
import com.center.medical.datamove.common.bean.model.MdDrinkingType;
import com.center.medical.datamove.common.service.MdDrinkingTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 饮酒种类(MdDrinkingType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
@Slf4j
@Service("mdDrinkingTypeService")
@RequiredArgsConstructor
public class MdDrinkingTypeServiceImpl extends ServiceImpl<MdDrinkingTypeMapper, MdDrinkingType> implements MdDrinkingTypeService {

    private final MdDrinkingTypeMapper mdDrinkingTypeMapper;

    /**
     * 分页查询[饮酒种类]列表
     *
     * @param page  分页参数
     * @param param MdDrinkingType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDrinkingType> getPage(PageParam<MdDrinkingType> page, MdDrinkingType param) {
        return mdDrinkingTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDrinkingType getInfoById(String id) {
        return mdDrinkingTypeMapper.getInfoById(id);
    }

}


