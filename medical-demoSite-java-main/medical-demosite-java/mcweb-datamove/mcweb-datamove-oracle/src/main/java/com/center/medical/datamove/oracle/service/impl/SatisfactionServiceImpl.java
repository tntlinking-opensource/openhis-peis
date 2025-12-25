package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SatisfactionMapper;
import com.center.medical.datamove.oracle.bean.model.Satisfaction;
import com.center.medical.datamove.oracle.service.SatisfactionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF满意度(Satisfaction)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:37
 */
@Slf4j
@Service("satisfactionService")
@RequiredArgsConstructor
public class SatisfactionServiceImpl extends ServiceImpl<SatisfactionMapper, Satisfaction> implements SatisfactionService {

    private final SatisfactionMapper satisfactionMapper;

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param Satisfaction查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Satisfaction> getPage(PageParam<Satisfaction> page, Satisfaction param) {
        return satisfactionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Satisfaction getInfoById(String id) {
        return satisfactionMapper.getInfoById(id);
    }

}


