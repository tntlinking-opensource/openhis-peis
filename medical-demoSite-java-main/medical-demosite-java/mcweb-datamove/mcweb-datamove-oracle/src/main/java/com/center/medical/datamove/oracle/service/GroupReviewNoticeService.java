package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.GroupReviewNotice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 批量职业健康检查复查通知书(GroupReviewNotice)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:06
 */
public interface GroupReviewNoticeService extends IService<GroupReviewNotice> {

    /**
     * 分页查询[批量职业健康检查复查通知书]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<GroupReviewNotice> getPage(PageParam<GroupReviewNotice> page, GroupReviewNotice param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    GroupReviewNotice getInfoById(String id);

}

