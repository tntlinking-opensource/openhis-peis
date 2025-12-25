package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsPeispatientMapper;
import com.center.medical.datamove.common.bean.model.MdPacsPeispatient;
import com.center.medical.datamove.common.service.MdPacsPeispatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-体检者表(MdPacsPeispatient)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
@Slf4j
@Service("mdPacsPeispatientService")
@RequiredArgsConstructor
public class MdPacsPeispatientServiceImpl extends ServiceImpl<MdPacsPeispatientMapper, MdPacsPeispatient> implements MdPacsPeispatientService {

    private final MdPacsPeispatientMapper mdPacsPeispatientMapper;

    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param MdPacsPeispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsPeispatient> getPage(PageParam<MdPacsPeispatient> page, MdPacsPeispatient param) {
        return mdPacsPeispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsPeispatient getInfoById(String id) {
        return mdPacsPeispatientMapper.getInfoById(id);
    }

    ;

}


