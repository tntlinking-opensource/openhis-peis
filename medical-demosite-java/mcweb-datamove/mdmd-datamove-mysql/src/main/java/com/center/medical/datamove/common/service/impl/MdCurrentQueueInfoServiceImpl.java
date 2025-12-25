package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCurrentQueueInfoMapper;
import com.center.medical.datamove.common.bean.model.MdCurrentQueueInfo;
import com.center.medical.datamove.common.service.MdCurrentQueueInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 当前排队绑定信息(MdCurrentQueueInfo)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
@Slf4j
@Service("mdCurrentQueueInfoService")
@RequiredArgsConstructor
public class MdCurrentQueueInfoServiceImpl extends ServiceImpl<MdCurrentQueueInfoMapper, MdCurrentQueueInfo> implements MdCurrentQueueInfoService {

    private final MdCurrentQueueInfoMapper mdCurrentQueueInfoMapper;

    /**
     * 分页查询[当前排队绑定信息]列表
     *
     * @param page  分页参数
     * @param param MdCurrentQueueInfo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCurrentQueueInfo> getPage(PageParam<MdCurrentQueueInfo> page, MdCurrentQueueInfo param) {
        return mdCurrentQueueInfoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCurrentQueueInfo getInfoById(String id) {
        return mdCurrentQueueInfoMapper.getInfoById(id);
    }

}


