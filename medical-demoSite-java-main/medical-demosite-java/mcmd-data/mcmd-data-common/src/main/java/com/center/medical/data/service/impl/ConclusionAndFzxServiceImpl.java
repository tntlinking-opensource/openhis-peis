package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ConclusionAndFzx;
import com.center.medical.data.dao.ConclusionAndFzxMapper;
import com.center.medical.data.service.ConclusionAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 10:39:05
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
    public IPage<ConclusionAndFzx> getList(PageParam<ConclusionAndFzx> page, ConclusionAndFzx param) {
        return conclusionAndFzxMapper.getList(page, param);
    }


}

