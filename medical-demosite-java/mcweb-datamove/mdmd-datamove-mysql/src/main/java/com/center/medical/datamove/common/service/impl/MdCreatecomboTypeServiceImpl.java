package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCreatecomboTypeMapper;
import com.center.medical.datamove.common.bean.model.MdCreatecomboType;
import com.center.medical.datamove.common.service.MdCreatecomboTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 最小套餐分类(MdCreatecomboType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
@Slf4j
@Service("mdCreatecomboTypeService")
@RequiredArgsConstructor
public class MdCreatecomboTypeServiceImpl extends ServiceImpl<MdCreatecomboTypeMapper, MdCreatecomboType> implements MdCreatecomboTypeService {

    private final MdCreatecomboTypeMapper mdCreatecomboTypeMapper;

    /**
     * 分页查询[最小套餐分类]列表
     *
     * @param page  分页参数
     * @param param MdCreatecomboType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCreatecomboType> getPage(PageParam<MdCreatecomboType> page, MdCreatecomboType param) {
        return mdCreatecomboTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCreatecomboType getInfoById(String id) {
        return mdCreatecomboTypeMapper.getInfoById(id);
    }

}


