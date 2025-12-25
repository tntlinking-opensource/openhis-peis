package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Harm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC危害因素(Harm)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:11
 */
public interface HarmService extends IService<Harm> {

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Harm> getPage(PageParam<Harm> page, Harm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Harm getInfoById(String id);

}

