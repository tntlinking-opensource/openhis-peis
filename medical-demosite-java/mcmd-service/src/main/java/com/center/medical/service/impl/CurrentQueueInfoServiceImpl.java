package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.CurrentQueueInfoMapper;
import com.center.medical.bean.model.CurrentQueueInfo;
import com.center.medical.service.CurrentQueueInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 当前排队绑定信息(CurrentQueueInfo)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:02
 */
@Slf4j
@Service("currentQueueInfoService")
@RequiredArgsConstructor
public class CurrentQueueInfoServiceImpl extends ServiceImpl<CurrentQueueInfoMapper, CurrentQueueInfo> implements CurrentQueueInfoService {

    private final CurrentQueueInfoMapper currentQueueInfoMapper;

    /**
     * 分页查询[当前排队绑定信息]列表
     *
     * @param page  分页参数
     * @param param CurrentQueueInfo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CurrentQueueInfo> getList(PageParam<CurrentQueueInfo> page, CurrentQueueInfo param) {
        return currentQueueInfoMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CurrentQueueInfo getInfoById(String id) {
        return currentQueueInfoMapper.getInfoById(id);
    }

}

