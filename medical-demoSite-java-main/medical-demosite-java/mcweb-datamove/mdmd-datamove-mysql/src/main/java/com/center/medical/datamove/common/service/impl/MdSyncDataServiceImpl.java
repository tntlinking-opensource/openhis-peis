package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSyncDataMapper;
import com.center.medical.datamove.common.bean.model.MdSyncData;
import com.center.medical.datamove.common.service.MdSyncDataService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步数据操作(MdSyncData)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:50
 */
@Slf4j
@Service("mdSyncDataService")
@RequiredArgsConstructor
public class MdSyncDataServiceImpl extends ServiceImpl<MdSyncDataMapper, MdSyncData> implements MdSyncDataService {

    private final MdSyncDataMapper mdSyncDataMapper;

    /**
     * 分页查询[同步数据操作]列表
     *
     * @param page  分页参数
     * @param param MdSyncData查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSyncData> getPage(PageParam<MdSyncData> page, MdSyncData param) {
        return mdSyncDataMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSyncData getInfoById(Long id) {
        return mdSyncDataMapper.getInfoById(id);
    }

}


