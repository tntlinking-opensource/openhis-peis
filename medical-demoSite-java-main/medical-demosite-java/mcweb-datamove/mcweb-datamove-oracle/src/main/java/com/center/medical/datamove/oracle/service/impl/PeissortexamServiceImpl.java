package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeissortexamMapper;
import com.center.medical.datamove.oracle.bean.model.Peissortexam;
import com.center.medical.datamove.oracle.service.PeissortexamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Peissortexam)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:34
 */
@Slf4j
@Service("peissortexamService")
@RequiredArgsConstructor
public class PeissortexamServiceImpl extends ServiceImpl<PeissortexamMapper, Peissortexam> implements PeissortexamService {

    private final PeissortexamMapper peissortexamMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Peissortexam查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peissortexam> getPage(PageParam<Peissortexam> page, Peissortexam param) {
        return peissortexamMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peissortexam getInfoById(String id) {
        return peissortexamMapper.getInfoById(id);
    }

}


