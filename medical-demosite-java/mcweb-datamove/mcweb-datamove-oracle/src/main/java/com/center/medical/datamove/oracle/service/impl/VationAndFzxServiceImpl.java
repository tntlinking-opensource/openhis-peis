package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.VationAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.VationAndFzx;
import com.center.medical.datamove.oracle.service.VationAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (VationAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:51
 */
@Slf4j
@Service("vationAndFzxService")
@RequiredArgsConstructor
public class VationAndFzxServiceImpl extends ServiceImpl<VationAndFzxMapper, VationAndFzx> implements VationAndFzxService {

    private final VationAndFzxMapper vationAndFzxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VationAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VationAndFzx> getPage(PageParam<VationAndFzx> page, VationAndFzx param) {
        return vationAndFzxMapper.getPage(page, param);
    }


}


