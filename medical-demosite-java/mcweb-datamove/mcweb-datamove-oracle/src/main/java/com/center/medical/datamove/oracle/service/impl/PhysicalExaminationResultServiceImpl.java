package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PhysicalExaminationResultMapper;
import com.center.medical.datamove.oracle.bean.model.PhysicalExaminationResult;
import com.center.medical.datamove.oracle.service.PhysicalExaminationResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检结果表（处理后）(PhysicalExaminationResult)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:35
 */
@Slf4j
@Service("physicalExaminationResultService")
@RequiredArgsConstructor
public class PhysicalExaminationResultServiceImpl extends ServiceImpl<PhysicalExaminationResultMapper, PhysicalExaminationResult> implements PhysicalExaminationResultService {

    private final PhysicalExaminationResultMapper physicalExaminationResultMapper;

    /**
     * 分页查询[体检结果表（处理后）]列表
     *
     * @param page  分页参数
     * @param param PhysicalExaminationResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PhysicalExaminationResult> getPage(PageParam<PhysicalExaminationResult> page, PhysicalExaminationResult param) {
        return physicalExaminationResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PhysicalExaminationResult getInfoById(String id) {
        return physicalExaminationResultMapper.getInfoById(id);
    }

}


