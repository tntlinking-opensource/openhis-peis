package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Pufftube;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 噗噗管(Pufftube)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:05
 */
public interface PufftubeService extends IService<Pufftube> {

    /**
     * 分页查询[噗噗管]列表
     *
     * @param page  分页参数
     * @param param Pufftube查询参数
     * @return 分页数据
     */
    IPage<Pufftube> getList(PageParam<Pufftube> page, Pufftube param);


}

