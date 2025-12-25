package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeispatient;
import com.center.medical.datamove.admin.dao.MdPeispatientMapper;
import com.center.medical.datamove.admin.service.MdPeispatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT体检者表(MdPeispatient)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:53:32
 */
@Slf4j
@Service("mdPeispatientService")
@RequiredArgsConstructor
public class MdPeispatientServiceImpl extends ServiceImpl<MdPeispatientMapper, MdPeispatient> implements MdPeispatientService {

    private final MdPeispatientMapper mdPeispatientMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatient> getPage(PageParam<MdPeispatient> page, MdPeispatient param) {
        return mdPeispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatient getInfoById(String id) {
        return mdPeispatientMapper.getInfoById(id);
    }


    /**
     * 批量保存体检者
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdPeispatient> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdPeispatient getByPatientCode(String patientCode) {
        return mdPeispatientMapper.selectOne(new LambdaQueryWrapper<MdPeispatient>().eq(MdPeispatient::getPatientcode,patientCode));
    }
}


