package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.GroupReviewNoticeMapper;
import com.center.medical.datamove.oracle.bean.model.GroupReviewNotice;
import com.center.medical.datamove.oracle.service.GroupReviewNoticeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量职业健康检查复查通知书(GroupReviewNotice)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:07
 */
@Slf4j
@Service("groupReviewNoticeService")
@RequiredArgsConstructor
public class GroupReviewNoticeServiceImpl extends ServiceImpl<GroupReviewNoticeMapper, GroupReviewNotice> implements GroupReviewNoticeService {

    private final GroupReviewNoticeMapper groupReviewNoticeMapper;

    /**
     * 分页查询[批量职业健康检查复查通知书]列表
     *
     * @param page  分页参数
     * @param param GroupReviewNotice查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupReviewNotice> getPage(PageParam<GroupReviewNotice> page, GroupReviewNotice param) {
        return groupReviewNoticeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public GroupReviewNotice getInfoById(String id) {
        return groupReviewNoticeMapper.getInfoById(id);
    }

}


