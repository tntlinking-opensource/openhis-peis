package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdGroupReviewNotice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 批量职业健康检查复查通知书(MdGroupReviewNotice)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
public interface MdGroupReviewNoticeService extends IService<MdGroupReviewNotice> {

    /**
     * 分页查询[批量职业健康检查复查通知书]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdGroupReviewNotice> getPage(PageParam<MdGroupReviewNotice> page, MdGroupReviewNotice param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdGroupReviewNotice getInfoById(String id);

}

