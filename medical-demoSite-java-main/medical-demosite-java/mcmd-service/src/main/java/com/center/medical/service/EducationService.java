package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Education;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 教育程度(Education)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:35
 */
public interface EducationService extends IService<Education> {

    /**
     * 分页查询[教育程度]列表
     *
     * @param page  分页参数
     * @param param Education查询参数
     * @return 分页数据
     */
    IPage<Education> getList(PageParam<Education> page, Education param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Education getInfoById(String id);

}

