package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Year;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 年份表(Year)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
public interface YearService extends IService<Year> {

    /**
     * 分页查询[年份表]列表
     *
     * @param page  分页参数
     * @param param Year查询参数
     * @return 分页数据
     */
    IPage<Year> getList(PageParam<Year> page, Year param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Year getInfoById(String id);

}

