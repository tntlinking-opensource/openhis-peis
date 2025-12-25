package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.SpecimenType;
import com.center.medical.data.bean.param.SpecimenTypeParam;
import com.center.medical.data.dao.SpecimenTypeMapper;
import com.center.medical.data.service.SpecimenTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * 标本种类(SpecimenType)服务实现类
 *
 * @author ay
 * @since 2023-11-07 15:49:17
 */
@Slf4j
@Service("specimenTypeService")
@RequiredArgsConstructor
public class SpecimenTypeServiceImpl extends ServiceImpl<SpecimenTypeMapper, SpecimenType> implements SpecimenTypeService {

    private final SpecimenTypeMapper specimenTypeMapper;

    /**
     * 分页查询[标本种类]列表
     *
     * @param page  分页参数
     * @param param SpecimenType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SpecimenType> getPage(PageParam<SpecimenType> page, SpecimenTypeParam param) {
        //去空格
        if (ObjectUtils.isNotEmpty(param) && ObjectUtils.isNotEmpty(param.getPinyinCode())){
            param.setPinyinCode(param.getPinyinCode().trim());
        }
        return specimenTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SpecimenType getInfoById(String id) {
        return specimenTypeMapper.getInfoById(id);
    }

}

