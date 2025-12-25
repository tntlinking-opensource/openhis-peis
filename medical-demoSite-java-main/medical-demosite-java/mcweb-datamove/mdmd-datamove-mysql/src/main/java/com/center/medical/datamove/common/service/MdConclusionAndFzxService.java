package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdConclusionAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC结伦词和分中心关联表(MdConclusionAndFzx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
public interface MdConclusionAndFzxService extends IService<MdConclusionAndFzx> {

    /**
     * 分页查询[JC结伦词和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdConclusionAndFzx> getPage(PageParam<MdConclusionAndFzx> page, MdConclusionAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdConclusionAndFzx getInfoById(String id);

}

