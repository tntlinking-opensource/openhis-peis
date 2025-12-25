package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzJzb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——家族病(WzJzb)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:02
 */
public interface WzJzbService extends IService<WzJzb> {

    /**
     * 分页查询[KS问诊——家族病]列表
     *
     * @param page  分页参数
     * @param param WzJzb查询参数
     * @return 分页数据
     */
    IPage<WzJzb> getList(PageParam<WzJzb> page, WzJzb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzJzb getInfoById(String id);

}

