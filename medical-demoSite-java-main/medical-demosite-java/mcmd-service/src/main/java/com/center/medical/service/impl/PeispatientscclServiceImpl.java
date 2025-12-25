package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientscclMapper;
import com.center.medical.bean.model.Peispatientsccl;
import com.center.medical.service.PeispatientscclService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检号生成策略(Peispatientsccl)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:04
 */
@Slf4j
@Service("peispatientscclService")
@RequiredArgsConstructor
public class PeispatientscclServiceImpl extends ServiceImpl<PeispatientscclMapper, Peispatientsccl> implements PeispatientscclService {

    private final PeispatientscclMapper peispatientscclMapper;

    /**
     * 分页查询[体检号生成策略]列表
     *
     * @param page  分页参数
     * @param param Peispatientsccl查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientsccl> getList(PageParam<Peispatientsccl> page, Peispatientsccl param) {
        return peispatientscclMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Peispatientsccl getInfoById(String id) {
        return peispatientscclMapper.getInfoById(id);
    }

}

