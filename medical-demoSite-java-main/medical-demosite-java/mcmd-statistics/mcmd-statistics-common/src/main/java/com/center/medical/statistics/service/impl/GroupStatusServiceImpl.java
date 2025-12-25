package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.GroupStatusParam;
import com.center.medical.statistics.bean.vo.GroupStatusVo;
import com.center.medical.statistics.dao.GroupStatusMapper;
import com.center.medical.statistics.service.GroupStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检状态明细(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("groupStatusService")
@RequiredArgsConstructor
public class GroupStatusServiceImpl extends ServiceImpl<GroupStatusMapper, Peispatient> implements GroupStatusService {

    private final GroupStatusMapper groupStatusMapper;
    private final SectionResultMainService sectionResultMainService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupStatusVo> getList(PageParam<GroupStatusVo> page, GroupStatusParam param) {
        IPage<GroupStatusVo> iPage = groupStatusMapper.getList(page, param);
        List<GroupStatusVo> list = iPage.getRecords();
        for (GroupStatusVo vo : list) {
            //体检状态
            Integer fReadytofinal = ObjectUtils.isEmpty(vo.getFReadytofinal()) ? 0 : vo.getFReadytofinal();
            Integer fRegistered = ObjectUtils.isEmpty(vo.getFRegistered()) ? 0 : vo.getFRegistered();
            if (fRegistered == 0) {
                vo.setTjzt("未检");
            } else if (fReadytofinal == 0) {
                vo.setTjzt("在检");
            } else {
                vo.setTjzt("分检完成");
            }
            //小结
            String healthSummarize = sectionResultMainService.getHealthSummarize(vo.getPatientcode());
            vo.setConclusions(healthSummarize);
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 导出体检者团体状态统计
     * @param param
     * @return
     */
    @Override
    public List<GroupStatusVo> exportData(GroupStatusParam param) {
        List<GroupStatusVo> list = groupStatusMapper.exportData(param);
        for (GroupStatusVo vo : list) {
            //体检状态
            Integer fReadytofinal = ObjectUtils.isEmpty(vo.getFReadytofinal()) ? 0 : vo.getFReadytofinal();
            Integer fRegistered = ObjectUtils.isEmpty(vo.getFRegistered()) ? 0 : vo.getFRegistered();
            if (fRegistered == 0) {
                vo.setTjzt("未检");
            } else if (fReadytofinal == 0) {
                vo.setTjzt("在检");
            } else {
                vo.setTjzt("分检完成");
            }
            //小结
            String healthSummarize = sectionResultMainService.getHealthSummarize(vo.getPatientcode());
            vo.setConclusions(healthSummarize);
        }
        return list;
    }
}

