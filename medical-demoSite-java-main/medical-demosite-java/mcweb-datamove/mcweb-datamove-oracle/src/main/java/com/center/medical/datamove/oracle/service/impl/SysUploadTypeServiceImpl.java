package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SysUploadTypeMapper;
import com.center.medical.datamove.oracle.bean.model.SysUploadType;
import com.center.medical.datamove.oracle.service.SysUploadTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据上传接收日志数据类型(SysUploadType)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:10
 */
@Slf4j
@Service("sysUploadTypeService")
@RequiredArgsConstructor
public class SysUploadTypeServiceImpl extends ServiceImpl<SysUploadTypeMapper, SysUploadType> implements SysUploadTypeService {

    private final SysUploadTypeMapper sysUploadTypeMapper;

    /**
     * 分页查询[数据上传接收日志数据类型]列表
     *
     * @param page  分页参数
     * @param param SysUploadType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUploadType> getPage(PageParam<SysUploadType> page, SysUploadType param) {
        return sysUploadTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysUploadType getInfoById(String id) {
        return sysUploadTypeMapper.getInfoById(id);
    }

}


