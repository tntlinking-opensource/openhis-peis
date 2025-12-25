package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.TotalDoctor;

/**
 * 总检-医生 关联表(TotalDoctor)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-08 17:32:58
 */
public interface TotalDoctorService extends IService<TotalDoctor> {

    /**
     * 分页查询[总检-医生 关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TotalDoctor> getPage(PageParam<TotalDoctor> page, TotalDoctor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TotalDoctor getInfoById(String id);

}

