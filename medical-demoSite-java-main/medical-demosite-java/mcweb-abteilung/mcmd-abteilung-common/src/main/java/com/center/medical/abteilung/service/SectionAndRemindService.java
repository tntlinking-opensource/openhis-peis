package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.SectionAndRemind;
import com.center.medical.common.utils.page.PageParam;

/**
 * 科室提醒和科室关联表(SectionAndRemind)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:17
 */
public interface SectionAndRemindService extends IService<SectionAndRemind> {

    /**
     * 分页查询[科室提醒和科室关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionAndRemind> getPage(PageParam<SectionAndRemind> page, SectionAndRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionAndRemind getInfoById(String id);

}

