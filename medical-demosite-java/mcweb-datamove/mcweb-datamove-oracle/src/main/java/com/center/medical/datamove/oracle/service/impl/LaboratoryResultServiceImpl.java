package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.LaboratoryResultMapper;
import com.center.medical.datamove.oracle.bean.model.LaboratoryResult;
import com.center.medical.datamove.oracle.service.LaboratoryResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS检验科接收数据(LaboratoryResult)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:33
 */
@Slf4j
@Service("laboratoryResultService")
@RequiredArgsConstructor
public class LaboratoryResultServiceImpl extends ServiceImpl<LaboratoryResultMapper, LaboratoryResult> implements LaboratoryResultService {

    private final LaboratoryResultMapper laboratoryResultMapper;

    /**
     * 分页查询[KS检验科接收数据]列表
     *
     * @param page  分页参数
     * @param param LaboratoryResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<LaboratoryResult> getPage(PageParam<LaboratoryResult> page, LaboratoryResult param) {
        return laboratoryResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public LaboratoryResult getInfoById(String id) {
        return laboratoryResultMapper.getInfoById(id);
    }

    ;

}


