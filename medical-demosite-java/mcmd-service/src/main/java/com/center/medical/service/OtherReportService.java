package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.OtherReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 其他报告(OtherReport)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:48
 */
public interface OtherReportService extends IService<OtherReport> {

    /**
     * 分页查询[其他报告]列表
     *
     * @param page  分页参数
     * @param param OtherReport查询参数
     * @return 分页数据
     */
    IPage<OtherReport> getList(PageParam<OtherReport> page, OtherReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OtherReport getInfoById(String id);

    /**
     * 根据体检号和类型查询
     * @param patientcode
     * @param type
     * @return
     */
    OtherReport getInfoByCode(String patientcode, String type);
}

