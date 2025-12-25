package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzCallback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——复查随访(WzCallback)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
public interface WzCallbackService extends IService<WzCallback> {

    /**
     * 分页查询[KS问诊——复查随访]列表
     *
     * @param page  分页参数
     * @param param WzCallback查询参数
     * @return 分页数据
     */
    IPage<WzCallback> getList(PageParam<WzCallback> page, WzCallback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzCallback getInfoById(String id);

}

