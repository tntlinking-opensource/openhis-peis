package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.VationAndFzxMapper;
import com.center.medical.bean.model.VationAndFzx;
import com.center.medical.service.VationAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 团体任务分中心（不会被同步）(VationAndFzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:46
 */
@Slf4j
@Service("vationAndFzxService")
@RequiredArgsConstructor
public class VationAndFzxServiceImpl extends ServiceImpl<VationAndFzxMapper, VationAndFzx> implements VationAndFzxService {

    private final VationAndFzxMapper vationAndFzxMapper;

    /**
     * 分页查询[团体任务分中心（不会被同步）]列表
     *
     * @param page  分页参数
     * @param param VationAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VationAndFzx> getList(PageParam<VationAndFzx> page, VationAndFzx param) {
        return vationAndFzxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public VationAndFzx getInfoById(String id) {
        return vationAndFzxMapper.getInfoById(id);
    }

}

