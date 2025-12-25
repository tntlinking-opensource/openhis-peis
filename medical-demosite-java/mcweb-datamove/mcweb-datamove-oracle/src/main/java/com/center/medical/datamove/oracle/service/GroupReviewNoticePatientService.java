package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.GroupReviewNoticePatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 批量复查通知人员(GroupReviewNoticePatient)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:08
 */
public interface GroupReviewNoticePatientService extends IService<GroupReviewNoticePatient> {

    /**
     * 分页查询[批量复查通知人员]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<GroupReviewNoticePatient> getPage(PageParam<GroupReviewNoticePatient> page, GroupReviewNoticePatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    GroupReviewNoticePatient getInfoById(String id);

}

