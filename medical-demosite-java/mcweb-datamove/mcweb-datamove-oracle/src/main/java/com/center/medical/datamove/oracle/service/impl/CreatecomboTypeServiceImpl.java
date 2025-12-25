package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CreatecomboTypeMapper;
import com.center.medical.datamove.oracle.bean.model.CreatecomboType;
import com.center.medical.datamove.oracle.service.CreatecomboTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 最小套餐分类(CreatecomboType)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:57
 */
@Slf4j
@Service("createcomboTypeService")
@RequiredArgsConstructor
public class CreatecomboTypeServiceImpl extends ServiceImpl<CreatecomboTypeMapper, CreatecomboType> implements CreatecomboTypeService {

    private final CreatecomboTypeMapper createcomboTypeMapper;

    /**
     * 分页查询[最小套餐分类]列表
     *
     * @param page  分页参数
     * @param param CreatecomboType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CreatecomboType> getPage(PageParam<CreatecomboType> page, CreatecomboType param) {
        return createcomboTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CreatecomboType getInfoById(String id) {
        return createcomboTypeMapper.getInfoById(id);
    }

}


