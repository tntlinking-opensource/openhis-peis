package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TeamremindMapper;
import com.center.medical.datamove.oracle.bean.model.Teamremind;
import com.center.medical.datamove.oracle.service.TeamremindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户预检跟踪表(Teamremind)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:14
 */
@Slf4j
@Service("teamremindService")
@RequiredArgsConstructor
public class TeamremindServiceImpl extends ServiceImpl<TeamremindMapper, Teamremind> implements TeamremindService {

    private final TeamremindMapper teamremindMapper;

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page  分页参数
     * @param param Teamremind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Teamremind> getPage(PageParam<Teamremind> page, Teamremind param) {
        return teamremindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Teamremind getInfoById(String id) {
        return teamremindMapper.getInfoById(id);
    }

    ;

}


