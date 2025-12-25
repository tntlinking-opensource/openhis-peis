package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Pufftube;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (Pufftube)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:41
 */
public interface PufftubeService extends IService<Pufftube> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Pufftube> getPage(PageParam<Pufftube> page, Pufftube param);


}

