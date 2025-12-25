package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PaPeissortexamMapper;
import com.center.medical.datamove.oracle.bean.model.PaPeissortexam;
import com.center.medical.datamove.oracle.service.PaPeissortexamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PaPeissortexam)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:37
 */
@Slf4j
@Service("paPeissortexamService")
@RequiredArgsConstructor
public class PaPeissortexamServiceImpl extends ServiceImpl<PaPeissortexamMapper, PaPeissortexam> implements PaPeissortexamService {

    private final PaPeissortexamMapper paPeissortexamMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PaPeissortexam查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PaPeissortexam> getPage(PageParam<PaPeissortexam> page, PaPeissortexam param) {
        return paPeissortexamMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PaPeissortexam getInfoById(String id) {
        return paPeissortexamMapper.getInfoById(id);
    }

}


