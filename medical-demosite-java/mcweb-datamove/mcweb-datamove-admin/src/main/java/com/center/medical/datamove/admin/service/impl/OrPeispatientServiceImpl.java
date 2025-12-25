package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Peispatient;
import com.center.medical.datamove.admin.dao.OrPeispatientMapper;
import com.center.medical.datamove.admin.service.OrPeispatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:55:00
 */
@Slf4j
@Service("orPeispatientService")
@RequiredArgsConstructor
public class OrPeispatientServiceImpl extends ServiceImpl<OrPeispatientMapper, Peispatient> implements OrPeispatientService {

    private final OrPeispatientMapper orPeispatientMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param) {
        return orPeispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return orPeispatientMapper.getInfoById(id);
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
        //一个分组下,未完成登记，未被禁检的体检者
        return orPeispatientMapper.getByGroupId(groupId);
    }
}


