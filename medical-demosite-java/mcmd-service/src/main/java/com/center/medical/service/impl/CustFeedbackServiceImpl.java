package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.CustFeedbackMapper;
import com.center.medical.bean.model.CustFeedback;
import com.center.medical.service.CustFeedbackService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 故障反馈(CustFeedback)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
@Slf4j
@Service("custFeedbackService")
@RequiredArgsConstructor
public class CustFeedbackServiceImpl extends ServiceImpl<CustFeedbackMapper, CustFeedback> implements CustFeedbackService {

    private final CustFeedbackMapper custFeedbackMapper;

    /**
     * 分页查询[故障反馈]列表
     *
     * @param page  分页参数
     * @param param CustFeedback查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CustFeedback> getList(PageParam<CustFeedback> page, CustFeedback param) {
        return custFeedbackMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CustFeedback getInfoById(String id) {
        return custFeedbackMapper.getInfoById(id);
    }

}

