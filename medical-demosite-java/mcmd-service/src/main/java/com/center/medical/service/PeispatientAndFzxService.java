package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeispatientAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 分组分中心(PeispatientAndFzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:16
 */
public interface PeispatientAndFzxService extends IService<PeispatientAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param PeispatientAndFzx查询参数
     * @return 分页数据
     */
    IPage<PeispatientAndFzx> getList(PageParam<PeispatientAndFzx> page, PeispatientAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientAndFzx getInfoById(String id);

}

