package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Peisorgreservationgroup;
import com.center.medical.datamove.admin.dao.OrPeisorgreservationgroupMapper;
import com.center.medical.datamove.admin.service.OrPeisorgreservationgroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT体检团体任务分组表(Peisorgreservationgroup)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:54:59
 */
@Slf4j
@Service("orPeisorgreservationgroupService")
@RequiredArgsConstructor
public class OrPeisorgreservationgroupServiceImpl extends ServiceImpl<OrPeisorgreservationgroupMapper, Peisorgreservationgroup> implements OrPeisorgreservationgroupService {

    private final OrPeisorgreservationgroupMapper orPeisorgreservationgroupMapper;

    /**
     * 分页查询[QT体检团体任务分组表]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationgroup查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peisorgreservationgroup> getPage(PageParam<Peisorgreservationgroup> page, Peisorgreservationgroup param) {
        return orPeisorgreservationgroupMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peisorgreservationgroup getInfoById(String id) {
        return orPeisorgreservationgroupMapper.getInfoById(id);
    }


    /**
     * 根据任务id获取分组
     *
     * @param taskId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Peisorgreservationgroup> getGroupList(String taskId) {
        List<Peisorgreservationgroup> peisorgreservationgroups = orPeisorgreservationgroupMapper.selectList(new LambdaQueryWrapper<Peisorgreservationgroup>()
                .eq(Peisorgreservationgroup::getIdOrgreservation, taskId)
                .eq(Peisorgreservationgroup::getIsDelete, 0));
        return peisorgreservationgroups;
    }

}


