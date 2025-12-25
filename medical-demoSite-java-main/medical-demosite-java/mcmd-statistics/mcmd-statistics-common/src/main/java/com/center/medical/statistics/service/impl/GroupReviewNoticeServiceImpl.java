package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.statistics.bean.param.CreateReviewGroupParam;
import com.center.medical.statistics.bean.param.GroupReviewNoticeParam;
import com.center.medical.statistics.bean.vo.GroupReviewNoticeVo;
import com.center.medical.statistics.dao.GroupReviewNoticeMapper;
import com.center.medical.bean.model.GroupReviewNotice;
import com.center.medical.statistics.service.GroupReviewNoticeService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量职业健康检查复查通知书(GroupReviewNotice)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
@Slf4j
@Service("groupReviewNoticeService")
@RequiredArgsConstructor
public class GroupReviewNoticeServiceImpl extends ServiceImpl<GroupReviewNoticeMapper, GroupReviewNotice> implements GroupReviewNoticeService {

    private final GroupReviewNoticeMapper groupReviewNoticeMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[批量职业健康检查复查通知书]列表
     *
     * @param page  分页参数
     * @param param GroupReviewNotice查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupReviewNoticeVo> getList(PageParam<GroupReviewNoticeVo> page, GroupReviewNoticeParam param) {
        return groupReviewNoticeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public GroupReviewNotice getInfoById(String id) {
        return groupReviewNoticeMapper.getInfoById(id);
    }

    /**
     * 创建名单
     * @param param
     * @return
     */
    @Override
    public String createReviewGroup(CreateReviewGroupParam param) {
        GroupReviewNotice group = mapperFacade.map(param, GroupReviewNotice.class);
        group.setCreator(SecurityUtils.getUsername());
        group.setStatus(0);
        group.setConfigId(param.getFzxId());
        groupReviewNoticeMapper.insert(group);
        return group.getId();
    }

    ;

}

