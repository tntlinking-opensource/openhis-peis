package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.CustFeedback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 故障反馈(CustFeedback)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
public interface CustFeedbackService extends IService<CustFeedback> {

    /**
     * 分页查询[故障反馈]列表
     *
     * @param page  分页参数
     * @param param CustFeedback查询参数
     * @return 分页数据
     */
    IPage<CustFeedback> getList(PageParam<CustFeedback> page, CustFeedback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CustFeedback getInfoById(String id);

}

