package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzJwb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——既往病(WzJwb)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:52
 */
public interface WzJwbService extends IService<WzJwb> {

    /**
     * 分页查询[KS问诊——既往病]列表
     *
     * @param page  分页参数
     * @param param WzJwb查询参数
     * @return 分页数据
     */
    IPage<WzJwb> getList(PageParam<WzJwb> page, WzJwb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzJwb getInfoById(String id);

}

