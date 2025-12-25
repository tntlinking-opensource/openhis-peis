package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Peispatient;
import com.center.medical.datamove.oracle.dao.PeispatientMapper;
import com.center.medical.datamove.oracle.service.PeispatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:09
 */
@Slf4j
@Service("peispatientService")
@RequiredArgsConstructor
public class PeispatientServiceImpl extends ServiceImpl<PeispatientMapper, Peispatient> implements PeispatientService {

    private final PeispatientMapper peispatientMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param) {
        return peispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return peispatientMapper.getInfoById(id);
    }

    /**
     * 通过分组id获取体检者
     *
     * @param groupId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Peispatient> getByGroupId(String groupId) {
        //一个分组下,未完成登记的体检者
        List<Peispatient> peispatients = peispatientMapper.selectList(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getIdOrgreservationgroup, groupId)
                .ne(Peispatient::getFRegistered, 1));
        return peispatients;
    }
}


