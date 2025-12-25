package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsPeispatientMapper;
import com.center.medical.datamove.oracle.bean.model.PacsPeispatient;
import com.center.medical.datamove.oracle.service.PacsPeispatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsPeispatient)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:49
 */
@Slf4j
@Service("pacsPeispatientService")
@RequiredArgsConstructor
public class PacsPeispatientServiceImpl extends ServiceImpl<PacsPeispatientMapper, PacsPeispatient> implements PacsPeispatientService {

    private final PacsPeispatientMapper pacsPeispatientMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsPeispatient> getPage(PageParam<PacsPeispatient> page, PacsPeispatient param) {
        return pacsPeispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsPeispatient getInfoById(String id) {
        return pacsPeispatientMapper.getInfoById(id);
    }

}


