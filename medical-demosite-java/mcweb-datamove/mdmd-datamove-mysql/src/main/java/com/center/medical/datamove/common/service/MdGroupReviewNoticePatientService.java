package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdGroupReviewNoticePatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 批量复查通知人员(MdGroupReviewNoticePatient)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
public interface MdGroupReviewNoticePatientService extends IService<MdGroupReviewNoticePatient> {

    /**
     * 分页查询[批量复查通知人员]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdGroupReviewNoticePatient> getPage(PageParam<MdGroupReviewNoticePatient> page, MdGroupReviewNoticePatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdGroupReviewNoticePatient getInfoById(String id);

}

