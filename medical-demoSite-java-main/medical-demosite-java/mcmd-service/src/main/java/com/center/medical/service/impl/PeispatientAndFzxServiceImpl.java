package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientAndFzxMapper;
import com.center.medical.bean.model.PeispatientAndFzx;
import com.center.medical.service.PeispatientAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分组分中心(PeispatientAndFzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:16
 */
@Slf4j
@Service("peispatientAndFzxService")
@RequiredArgsConstructor
public class PeispatientAndFzxServiceImpl extends ServiceImpl<PeispatientAndFzxMapper, PeispatientAndFzx> implements PeispatientAndFzxService {

    private final PeispatientAndFzxMapper peispatientAndFzxMapper;

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param PeispatientAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientAndFzx> getList(PageParam<PeispatientAndFzx> page, PeispatientAndFzx param) {
        return peispatientAndFzxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientAndFzx getInfoById(String id) {
        return peispatientAndFzxMapper.getInfoById(id);
    }

    ;

}

