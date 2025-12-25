package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.GroupReviewNoticePatientMapper;
import com.center.medical.bean.model.GroupReviewNoticePatient;
import com.center.medical.service.GroupReviewNoticePatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量复查通知人员(GroupReviewNoticePatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
@Slf4j
@Service("groupReviewNoticePatientService")
@RequiredArgsConstructor
public class GroupReviewNoticePatientServiceImpl extends ServiceImpl<GroupReviewNoticePatientMapper, GroupReviewNoticePatient> implements GroupReviewNoticePatientService {

    private final GroupReviewNoticePatientMapper groupReviewNoticePatientMapper;

    /**
     * 分页查询[批量复查通知人员]列表
     *
     * @param page  分页参数
     * @param param GroupReviewNoticePatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupReviewNoticePatient> getList(PageParam<GroupReviewNoticePatient> page, GroupReviewNoticePatient param) {
        return groupReviewNoticePatientMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public GroupReviewNoticePatient getInfoById(String id) {
        return groupReviewNoticePatientMapper.getInfoById(id);
    }

}

