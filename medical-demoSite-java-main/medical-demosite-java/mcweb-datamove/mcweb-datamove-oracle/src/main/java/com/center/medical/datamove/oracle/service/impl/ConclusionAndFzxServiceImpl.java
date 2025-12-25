package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ConclusionAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.ConclusionAndFzx;
import com.center.medical.datamove.oracle.service.ConclusionAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:47
 */
@Slf4j
@Service("conclusionAndFzxService")
@RequiredArgsConstructor
public class ConclusionAndFzxServiceImpl extends ServiceImpl<ConclusionAndFzxMapper, ConclusionAndFzx> implements ConclusionAndFzxService {

    private final ConclusionAndFzxMapper conclusionAndFzxMapper;

    /**
     * 分页查询[JC结伦词和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ConclusionAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ConclusionAndFzx> getPage(PageParam<ConclusionAndFzx> page, ConclusionAndFzx param) {
        return conclusionAndFzxMapper.getPage(page, param);
    }


}


