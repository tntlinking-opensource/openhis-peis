package com.center.medical.sync.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncDataType;
import com.center.medical.sync.dao.SyncDataTypeMapper;
import com.center.medical.sync.service.SyncDataTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 同步数据类型(SyncDataType)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:34
 */
@Slf4j
@Service("syncLcDataTypeService")
@RequiredArgsConstructor
public class SyncDataTypeServiceImpl extends ServiceImpl<SyncDataTypeMapper, SyncDataType> implements SyncDataTypeService {

    private final SyncDataTypeMapper syncDataTypeMapper;

    /**
     * 分页查询[同步数据类型]列表
     *
     * @param page  分页参数
     * @param param SyncDataType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SyncDataType> getPage(PageParam<SyncDataType> page, SyncDataType param) {
        return syncDataTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    @Override
    public SyncDataType getInfoById(Integer id) {
        return syncDataTypeMapper.getInfoById(id);
    }

    @Override
    public void updateDiy(SyncDataType syncDataType) {
        syncDataType.setTypeName("diyUpdate");
        syncDataType.setTypeId(6);
        syncDataTypeMapper.updateDiy(syncDataType);
        syncDataTypeMapper.updateALL();
    }

}

