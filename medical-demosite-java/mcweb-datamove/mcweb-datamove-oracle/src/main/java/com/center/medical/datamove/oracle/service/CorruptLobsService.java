package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.CorruptLobs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (CorruptLobs)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:51
 */
public interface CorruptLobsService extends IService<CorruptLobs> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CorruptLobs> getPage(PageParam<CorruptLobs> page, CorruptLobs param);


}

