package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.DiningRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 就餐记录(DiningRecord)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:20
 */
public interface DiningRecordService extends IService<DiningRecord> {

    /**
     * 分页查询[就餐记录]列表
     *
     * @param page  分页参数
     * @param param DiningRecord查询参数
     * @return 分页数据
     */
    IPage<DiningRecord> getList(PageParam<DiningRecord> page, DiningRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DiningRecord getInfoById(String id);

}

