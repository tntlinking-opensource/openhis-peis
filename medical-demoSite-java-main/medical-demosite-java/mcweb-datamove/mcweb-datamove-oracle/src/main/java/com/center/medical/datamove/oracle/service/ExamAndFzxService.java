package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ExamAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC检查项目和分中心关联表(ExamAndFzx)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:38
 */
public interface ExamAndFzxService extends IService<ExamAndFzx> {

    /**
     * 分页查询[JC检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ExamAndFzx> getPage(PageParam<ExamAndFzx> page, ExamAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ExamAndFzx getInfoById(String id);

}

