package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysDictTypeMapper;
import com.center.medical.datamove.common.bean.model.SysDictType;
import com.center.medical.datamove.common.service.SysDictTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典类型表(SysDictType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Slf4j
@Service("sysDictTypeService")
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;

    /**
     * 分页查询[字典类型表]列表
     *
     * @param page  分页参数
     * @param param SysDictType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysDictType> getPage(PageParam<SysDictType> page, SysDictType param) {
        return sysDictTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键dictId
     * @return 详情信息
     */
    @Override
    public SysDictType getInfoById(Long id) {
        return sysDictTypeMapper.getInfoById(id);
    }

}


