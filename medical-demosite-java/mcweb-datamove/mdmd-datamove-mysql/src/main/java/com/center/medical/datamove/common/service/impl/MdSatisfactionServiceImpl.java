package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSatisfactionMapper;
import com.center.medical.datamove.common.bean.model.MdSatisfaction;
import com.center.medical.datamove.common.service.MdSatisfactionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF满意度(MdSatisfaction)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:27
 */
@Slf4j
@Service("mdSatisfactionService")
@RequiredArgsConstructor
public class MdSatisfactionServiceImpl extends ServiceImpl<MdSatisfactionMapper, MdSatisfaction> implements MdSatisfactionService {

    private final MdSatisfactionMapper mdSatisfactionMapper;

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param MdSatisfaction查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSatisfaction> getPage(PageParam<MdSatisfaction> page, MdSatisfaction param) {
        return mdSatisfactionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSatisfaction getInfoById(String id) {
        return mdSatisfactionMapper.getInfoById(id);
    }

}


