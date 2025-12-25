package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.TTjZyzlqkb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (TTjZyzlqkb)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:11
 */
public interface TTjZyzlqkbService extends IService<TTjZyzlqkb> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TTjZyzlqkb> getPage(PageParam<TTjZyzlqkb> page, TTjZyzlqkb param);


}

