package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ConclusionAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:47
 */
public interface ConclusionAndFzxService extends IService<ConclusionAndFzx> {

    /**
     * 分页查询[JC结伦词和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ConclusionAndFzx> getPage(PageParam<ConclusionAndFzx> page, ConclusionAndFzx param);


}

