package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmTeamremindMapper;
import com.center.medical.datamove.common.bean.model.CrmTeamremind;
import com.center.medical.datamove.common.service.CrmTeamremindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户预检跟踪表(CrmTeamremind)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
@Slf4j
@Service("crmTeamremindService")
@RequiredArgsConstructor
public class CrmTeamremindServiceImpl extends ServiceImpl<CrmTeamremindMapper, CrmTeamremind> implements CrmTeamremindService {

    private final CrmTeamremindMapper crmTeamremindMapper;

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page  分页参数
     * @param param CrmTeamremind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmTeamremind> getPage(PageParam<CrmTeamremind> page, CrmTeamremind param) {
        return crmTeamremindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmTeamremind getInfoById(String id) {
        return crmTeamremindMapper.getInfoById(id);
    }

}


