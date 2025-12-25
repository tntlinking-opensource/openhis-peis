package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Nation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC民族(Nation)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
public interface NationService extends IService<Nation> {

    /**
     * 分页查询[JC民族]列表
     *
     * @param page  分页参数
     * @param param Nation查询参数
     * @return 分页数据
     */
    IPage<Nation> getList(PageParam<Nation> page, Nation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Nation getInfoById(String id);

}

