package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSampleConnectMapper;
import com.center.medical.datamove.common.bean.model.MdSampleConnect;
import com.center.medical.datamove.common.service.MdSampleConnectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS样本交接(MdSampleConnect)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:23
 */
@Slf4j
@Service("mdSampleConnectService")
@RequiredArgsConstructor
public class MdSampleConnectServiceImpl extends ServiceImpl<MdSampleConnectMapper, MdSampleConnect> implements MdSampleConnectService {

    private final MdSampleConnectMapper mdSampleConnectMapper;

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param MdSampleConnect查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSampleConnect> getPage(PageParam<MdSampleConnect> page, MdSampleConnect param) {
        return mdSampleConnectMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSampleConnect getInfoById(String id) {
        return mdSampleConnectMapper.getInfoById(id);
    }

}


