package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ZyFhcsGc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC工程防护(ZyFhcsGc)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
public interface ZyFhcsGcService extends IService<ZyFhcsGc> {

    /**
     * 分页查询[JC工程防护]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGc查询参数
     * @return 分页数据
     */
    IPage<ZyFhcsGc> getList(PageParam<ZyFhcsGc> page, ZyFhcsGc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZyFhcsGc getInfoById(String id);

}

