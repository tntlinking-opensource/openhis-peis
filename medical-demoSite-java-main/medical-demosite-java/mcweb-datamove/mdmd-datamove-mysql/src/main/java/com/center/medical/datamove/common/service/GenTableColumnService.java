package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.GenTableColumn;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 代码生成业务表字段(GenTableColumn)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
public interface GenTableColumnService extends IService<GenTableColumn> {

    /**
     * 分页查询[代码生成业务表字段]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<GenTableColumn> getPage(PageParam<GenTableColumn> page, GenTableColumn param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键columnId
     * @return 详情信息
     */
    GenTableColumn getInfoById(Long id);

}

