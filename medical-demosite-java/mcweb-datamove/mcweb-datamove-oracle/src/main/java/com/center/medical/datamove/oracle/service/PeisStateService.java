package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeisState;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者上传状态(PeisState)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:03
 */
public interface PeisStateService extends IService<PeisState> {

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeisState> getPage(PageParam<PeisState> page, PeisState param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisState getInfoById(String id);

}

