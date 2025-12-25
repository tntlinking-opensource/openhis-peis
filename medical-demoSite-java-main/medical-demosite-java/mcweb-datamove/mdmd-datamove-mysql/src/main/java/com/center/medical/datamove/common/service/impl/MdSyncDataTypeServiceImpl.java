package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSyncDataTypeMapper;
import com.center.medical.datamove.common.bean.model.MdSyncDataType;
import com.center.medical.datamove.common.service.MdSyncDataTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步数据类型(MdSyncDataType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:53
 */
@Slf4j
@Service("mdSyncDataTypeService")
@RequiredArgsConstructor
public class MdSyncDataTypeServiceImpl extends ServiceImpl<MdSyncDataTypeMapper, MdSyncDataType> implements MdSyncDataTypeService {

    private final MdSyncDataTypeMapper mdSyncDataTypeMapper;

    /**
     * 分页查询[同步数据类型]列表
     *
     * @param page  分页参数
     * @param param MdSyncDataType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSyncDataType> getPage(PageParam<MdSyncDataType> page, MdSyncDataType param) {
        return mdSyncDataTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    @Override
    public MdSyncDataType getInfoById(Integer id) {
        return mdSyncDataTypeMapper.getInfoById(id);
    }

}


