package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Kingdeecustomer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (Kingdeecustomer)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:24
 */
public interface KingdeecustomerService extends IService<Kingdeecustomer> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Kingdeecustomer> getPage(PageParam<Kingdeecustomer> page, Kingdeecustomer param);


}

