package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Peispatientfeeitem;
import com.center.medical.datamove.oracle.dao.PeispatientfeeitemMapper;
import com.center.medical.datamove.oracle.service.PeispatientfeeitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:29
 */
@Slf4j
@Service("peispatientfeeitemService")
@RequiredArgsConstructor
public class PeispatientfeeitemServiceImpl extends ServiceImpl<PeispatientfeeitemMapper, Peispatientfeeitem> implements PeispatientfeeitemService {

    private final PeispatientfeeitemMapper peispatientfeeitemMapper;

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientfeeitem> getPage(PageParam<Peispatientfeeitem> page, Peispatientfeeitem param) {
        return peispatientfeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientfeeitem getInfoById(String id) {
        return peispatientfeeitemMapper.getInfoById(id);
    }


    /**
     * 通过体检号获取收费项目
     *
     * @param patientcode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Peispatientfeeitem> getByPatientCode(String patientcode) {
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, patientcode));
        return peispatientfeeitems;
    }
}


