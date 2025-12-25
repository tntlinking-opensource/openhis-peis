package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzTjrecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——体检记录(WzTjrecord)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:07
 */
public interface WzTjrecordService extends IService<WzTjrecord> {

    /**
     * 分页查询[KS问诊——体检记录]列表
     *
     * @param page  分页参数
     * @param param WzTjrecord查询参数
     * @return 分页数据
     */
    IPage<WzTjrecord> getList(PageParam<WzTjrecord> page, WzTjrecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzTjrecord getInfoById(String id);

}

