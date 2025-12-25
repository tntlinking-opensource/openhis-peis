package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CreatecomboType;
import com.center.medical.sellcrm.bean.vo.TypeListVo;
import com.center.medical.sellcrm.dao.CreatecomboTypeMapper;
import com.center.medical.sellcrm.service.CreatecomboTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 最小套餐分类(CreatecomboType)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:18
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
    public IPage<CreatecomboType> getList(PageParam<CreatecomboType> page, CreatecomboType param) {
        return createcomboTypeMapper.getList(page, param);
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

    /**
     * 获取最小套餐分类
     *
     * @param page
     * @return
     */
    @Override
    public IPage<TypeListVo> getTypeList(PageParam<TypeListVo> page) {
        return createcomboTypeMapper.getTypeList(page);
    }
}

