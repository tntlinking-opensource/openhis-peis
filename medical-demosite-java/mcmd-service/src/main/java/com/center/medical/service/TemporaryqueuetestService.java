package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Temporaryqueuetest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * temporaryqueuetest(Temporaryqueuetest)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
public interface TemporaryqueuetestService extends IService<Temporaryqueuetest> {

    /**
     * 分页查询[temporaryqueuetest]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueuetest查询参数
     * @return 分页数据
     */
    IPage<Temporaryqueuetest> getList(PageParam<Temporaryqueuetest> page, Temporaryqueuetest param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Temporaryqueuetest getInfoById(String id);

}

