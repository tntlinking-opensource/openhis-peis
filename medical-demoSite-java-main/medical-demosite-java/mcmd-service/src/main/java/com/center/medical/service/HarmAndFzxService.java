package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.HarmAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 危害因素和分中心(HarmAndFzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface HarmAndFzxService extends IService<HarmAndFzx> {

    /**
     * 分页查询[危害因素和分中心]列表
     *
     * @param page  分页参数
     * @param param HarmAndFzx查询参数
     * @return 分页数据
     */
    IPage<HarmAndFzx> getList(PageParam<HarmAndFzx> page, HarmAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    HarmAndFzx getInfoById(String id);

}

