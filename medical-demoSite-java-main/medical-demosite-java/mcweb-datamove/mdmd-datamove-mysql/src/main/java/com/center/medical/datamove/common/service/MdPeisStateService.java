package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeisState;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者上传状态(MdPeisState)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:08
 */
public interface MdPeisStateService extends IService<MdPeisState> {

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeisState> getPage(PageParam<MdPeisState> page, MdPeisState param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisState getInfoById(String id);

}

