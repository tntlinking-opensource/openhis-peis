package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.GenTableColumnMapper;
import com.center.medical.datamove.common.bean.model.GenTableColumn;
import com.center.medical.datamove.common.service.GenTableColumnService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码生成业务表字段(GenTableColumn)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
@Slf4j
@Service("genTableColumnService")
@RequiredArgsConstructor
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements GenTableColumnService {

    private final GenTableColumnMapper genTableColumnMapper;

    /**
     * 分页查询[代码生成业务表字段]列表
     *
     * @param page  分页参数
     * @param param GenTableColumn查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GenTableColumn> getPage(PageParam<GenTableColumn> page, GenTableColumn param) {
        return genTableColumnMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键columnId
     * @return 详情信息
     */
    @Override
    public GenTableColumn getInfoById(Long id) {
        return genTableColumnMapper.getInfoById(id);
    }

}


