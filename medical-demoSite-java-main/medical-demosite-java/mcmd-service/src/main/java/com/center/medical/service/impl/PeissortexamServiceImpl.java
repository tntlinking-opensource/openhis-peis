package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeissortexamMapper;
import com.center.medical.bean.model.Peissortexam;
import com.center.medical.service.PeissortexamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 排检(Peissortexam)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:28
 */
@Slf4j
@Service("peissortexamService")
@RequiredArgsConstructor
public class PeissortexamServiceImpl extends ServiceImpl<PeissortexamMapper, Peissortexam> implements PeissortexamService {

    private final PeissortexamMapper peissortexamMapper;

    /**
     * 分页查询[排检]列表
     *
     * @param page  分页参数
     * @param param Peissortexam查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peissortexam> getList(PageParam<Peissortexam> page, Peissortexam param) {
        return peissortexamMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Peissortexam getInfoById(String id) {
        return peissortexamMapper.getInfoById(id);
    }

}

