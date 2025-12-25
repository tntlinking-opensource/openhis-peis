package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeisQuestionnaireSecondMapper;
import com.center.medical.datamove.oracle.bean.model.PeisQuestionnaireSecond;
import com.center.medical.datamove.oracle.service.PeisQuestionnaireSecondService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 健康体检问卷(PeisQuestionnaireSecond)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:00
 */
@Slf4j
@Service("peisQuestionnaireSecondService")
@RequiredArgsConstructor
public class PeisQuestionnaireSecondServiceImpl extends ServiceImpl<PeisQuestionnaireSecondMapper, PeisQuestionnaireSecond> implements PeisQuestionnaireSecondService {

    private final PeisQuestionnaireSecondMapper peisQuestionnaireSecondMapper;

    /**
     * 分页查询[健康体检问卷]列表
     *
     * @param page  分页参数
     * @param param PeisQuestionnaireSecond查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisQuestionnaireSecond> getPage(PageParam<PeisQuestionnaireSecond> page, PeisQuestionnaireSecond param) {
        return peisQuestionnaireSecondMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeisQuestionnaireSecond getInfoById(String id) {
        return peisQuestionnaireSecondMapper.getInfoById(id);
    }

}


