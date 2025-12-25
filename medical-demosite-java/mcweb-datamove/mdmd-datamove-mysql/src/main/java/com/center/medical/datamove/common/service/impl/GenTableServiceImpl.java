package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.GenTableMapper;
import com.center.medical.datamove.common.bean.model.GenTable;
import com.center.medical.datamove.common.service.GenTableService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码生成业务表(GenTable)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
@Slf4j
@Service("genTableService")
@RequiredArgsConstructor
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GenTableService {

    private final GenTableMapper genTableMapper;

    /**
     * 分页查询[代码生成业务表]列表
     *
     * @param page  分页参数
     * @param param GenTable查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GenTable> getPage(PageParam<GenTable> page, GenTable param) {
        return genTableMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键tableId
     * @return 详情信息
     */
    @Override
    public GenTable getInfoById(Long id) {
        return genTableMapper.getInfoById(id);
    }

}


