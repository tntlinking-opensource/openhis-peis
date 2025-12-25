package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ConclusionAndFzx;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 10:39:05
 */
public interface ConclusionAndFzxService extends IService<ConclusionAndFzx> {

    /**
     * 分页查询[JC结伦词和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ConclusionAndFzx查询参数
     * @return 分页数据
     */
    IPage<ConclusionAndFzx> getList(PageParam<ConclusionAndFzx> page, ConclusionAndFzx param);


}

