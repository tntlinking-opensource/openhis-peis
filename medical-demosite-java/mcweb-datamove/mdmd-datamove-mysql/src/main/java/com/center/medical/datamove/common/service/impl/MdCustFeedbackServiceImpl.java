package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCustFeedbackMapper;
import com.center.medical.datamove.common.bean.model.MdCustFeedback;
import com.center.medical.datamove.common.service.MdCustFeedbackService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 故障反馈(MdCustFeedback)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
@Slf4j
@Service("mdCustFeedbackService")
@RequiredArgsConstructor
public class MdCustFeedbackServiceImpl extends ServiceImpl<MdCustFeedbackMapper, MdCustFeedback> implements MdCustFeedbackService {

    private final MdCustFeedbackMapper mdCustFeedbackMapper;

    /**
     * 分页查询[故障反馈]列表
     *
     * @param page  分页参数
     * @param param MdCustFeedback查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCustFeedback> getPage(PageParam<MdCustFeedback> page, MdCustFeedback param) {
        return mdCustFeedbackMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCustFeedback getInfoById(String id) {
        return mdCustFeedbackMapper.getInfoById(id);
    }

}


