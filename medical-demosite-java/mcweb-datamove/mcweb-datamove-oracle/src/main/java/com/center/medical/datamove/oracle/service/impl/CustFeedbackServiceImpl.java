package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CustFeedbackMapper;
import com.center.medical.datamove.oracle.bean.model.CustFeedback;
import com.center.medical.datamove.oracle.service.CustFeedbackService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (CustFeedback)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:01
 */
@Slf4j
@Service("custFeedbackService")
@RequiredArgsConstructor
public class CustFeedbackServiceImpl extends ServiceImpl<CustFeedbackMapper, CustFeedback> implements CustFeedbackService {

    private final CustFeedbackMapper custFeedbackMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param CustFeedback查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CustFeedback> getPage(PageParam<CustFeedback> page, CustFeedback param) {
        return custFeedbackMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CustFeedback getInfoById(String id) {
        return custFeedbackMapper.getInfoById(id);
    }

}


