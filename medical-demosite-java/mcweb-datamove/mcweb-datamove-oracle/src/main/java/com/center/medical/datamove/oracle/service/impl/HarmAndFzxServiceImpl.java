package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.HarmAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.HarmAndFzx;
import com.center.medical.datamove.oracle.service.HarmAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (HarmAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:13
 */
@Slf4j
@Service("harmAndFzxService")
@RequiredArgsConstructor
public class HarmAndFzxServiceImpl extends ServiceImpl<HarmAndFzxMapper, HarmAndFzx> implements HarmAndFzxService {

    private final HarmAndFzxMapper harmAndFzxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param HarmAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HarmAndFzx> getPage(PageParam<HarmAndFzx> page, HarmAndFzx param) {
        return harmAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public HarmAndFzx getInfoById(String id) {
        return harmAndFzxMapper.getInfoById(id);
    }

}


