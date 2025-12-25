package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.KdSaleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 金蝶销售员(KdSaleer)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
public interface KdSaleerService extends IService<KdSaleer> {

    /**
     * 分页查询[金蝶销售员]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdSaleer> getPage(PageParam<KdSaleer> page, KdSaleer param);


}

