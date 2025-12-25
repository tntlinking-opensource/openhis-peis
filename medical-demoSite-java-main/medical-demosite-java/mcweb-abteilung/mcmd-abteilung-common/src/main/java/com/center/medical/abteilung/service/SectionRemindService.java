package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.SectionRemind;
import com.center.medical.common.utils.page.PageParam;

/**
 * 科室提醒主表(SectionRemind)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
public interface SectionRemindService extends IService<SectionRemind> {

    /**
     * 分页查询[科室提醒主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionRemind> getPage(PageParam<SectionRemind> page, SectionRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionRemind getInfoById(String id);

}

