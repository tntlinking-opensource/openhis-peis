package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientAndFzx;
import com.center.medical.datamove.oracle.service.PeispatientAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PeispatientAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:13
 */
@Slf4j
@Service("peispatientAndFzxService")
@RequiredArgsConstructor
public class PeispatientAndFzxServiceImpl extends ServiceImpl<PeispatientAndFzxMapper, PeispatientAndFzx> implements PeispatientAndFzxService {

    private final PeispatientAndFzxMapper peispatientAndFzxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientAndFzx> getPage(PageParam<PeispatientAndFzx> page, PeispatientAndFzx param) {
        return peispatientAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientAndFzx getInfoById(String id) {
        return peispatientAndFzxMapper.getInfoById(id);
    }

}


