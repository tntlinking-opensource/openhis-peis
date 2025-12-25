package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ExamAndFzx;

/**
 * 检查项目和分中心关联表(ExamAndFzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 10:52:39
 */
public interface ExamAndFzxService extends IService<ExamAndFzx> {

    /**
     * 分页查询[检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ExamAndFzx查询参数
     * @return 分页数据
     */
    IPage<ExamAndFzx> getList(PageParam<ExamAndFzx> page, ExamAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id ExamAndFzx查询参数
     * @return 分页数据
     */
    ExamAndFzx getInfoById(String id);

}

