package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBkPatientMapper;
import com.center.medical.datamove.common.bean.model.MdBkPatient;
import com.center.medical.datamove.common.service.MdBkPatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者(MdBkPatient)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
@Slf4j
@Service("mdBkPatientService")
@RequiredArgsConstructor
public class MdBkPatientServiceImpl extends ServiceImpl<MdBkPatientMapper, MdBkPatient> implements MdBkPatientService {

    private final MdBkPatientMapper mdBkPatientMapper;

    /**
     * 分页查询[体检者]列表
     *
     * @param page  分页参数
     * @param param MdBkPatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBkPatient> getPage(PageParam<MdBkPatient> page, MdBkPatient param) {
        return mdBkPatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBkPatient getInfoById(String id) {
        return mdBkPatientMapper.getInfoById(id);
    }

}


