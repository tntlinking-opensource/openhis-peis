package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPufftube;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 噗噗管(MdPufftube)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
public interface MdPufftubeService extends IService<MdPufftube> {

    /**
     * 分页查询[噗噗管]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPufftube> getPage(PageParam<MdPufftube> page, MdPufftube param);


}

