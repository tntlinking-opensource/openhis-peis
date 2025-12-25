package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.GroupReviewNoticePatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 批量复查通知人员(GroupReviewNoticePatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface GroupReviewNoticePatientService extends IService<GroupReviewNoticePatient> {

    /**
     * 分页查询[批量复查通知人员]列表
     *
     * @param page  分页参数
     * @param param GroupReviewNoticePatient查询参数
     * @return 分页数据
     */
    IPage<GroupReviewNoticePatient> getList(PageParam<GroupReviewNoticePatient> page, GroupReviewNoticePatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    GroupReviewNoticePatient getInfoById(String id);

}

