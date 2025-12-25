package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeisorgreservationgroup;
import com.center.medical.olddata.dao.OrPeisorgreservationgroupMapper;
import com.center.medical.olddata.service.OrPeisorgreservationgroupService;
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
public class OrPeisorgreservationgroupServiceImpl extends ServiceImpl<OrPeisorgreservationgroupMapper, OrPeisorgreservationgroup> implements OrPeisorgreservationgroupService {

    private final OrPeisorgreservationgroupMapper peisorgreservationgroupMapper;

    /**
     * 分页查询[QT体检团体任务分组表]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationgroup查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeisorgreservationgroup> getPage(PageParam<OrPeisorgreservationgroup> page, OrPeisorgreservationgroup param) {
        return peisorgreservationgroupMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrPeisorgreservationgroup getInfoById(String id) {
        return peisorgreservationgroupMapper.selectOne(new LambdaQueryWrapper<OrPeisorgreservationgroup>().eq(OrPeisorgreservationgroup::getId, id));
    }


    /**
     * 根据任务id获取分组
     *
     * @param taskId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeisorgreservationgroup> getGroupList(String taskId) {
        List<OrPeisorgreservationgroup> peisorgreservationgroups = peisorgreservationgroupMapper.selectList(new LambdaQueryWrapper<OrPeisorgreservationgroup>()
                .eq(OrPeisorgreservationgroup::getIdOrgreservation, taskId)
                .eq(OrPeisorgreservationgroup::getIsDelete, 0)
                .eq(OrPeisorgreservationgroup::getFGroupstarted, 1)
        );
        return peisorgreservationgroups;
    }

}


