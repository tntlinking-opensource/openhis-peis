package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.GroupReviewNotice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.CreateReviewGroupParam;
import com.center.medical.statistics.bean.param.GroupReviewNoticeParam;
import com.center.medical.statistics.bean.vo.GroupReviewNoticeVo;

/**
 * 批量职业健康检查复查通知书(GroupReviewNotice)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
public interface GroupReviewNoticeService extends IService<GroupReviewNotice> {

    /**
     * 分页查询[批量职业健康检查复查通知书]列表
     *
     * @param page  分页参数
     * @param param GroupReviewNotice查询参数
     * @return 分页数据
     */
    IPage<GroupReviewNoticeVo> getList(PageParam<GroupReviewNoticeVo> page, GroupReviewNoticeParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    GroupReviewNotice getInfoById(String id);

    /**
     * 创建名单
     * @param param
     * @return
     */
    String createReviewGroup(CreateReviewGroupParam param);
}

