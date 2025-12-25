package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCustFeedback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 故障反馈(MdCustFeedback)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
public interface MdCustFeedbackService extends IService<MdCustFeedback> {

    /**
     * 分页查询[故障反馈]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCustFeedback> getPage(PageParam<MdCustFeedback> page, MdCustFeedback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCustFeedback getInfoById(String id);

}

