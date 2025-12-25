package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FileChangeRecordMapper;
import com.center.medical.datamove.oracle.bean.model.FileChangeRecord;
import com.center.medical.datamove.oracle.service.FileChangeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (FileChangeRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:44
 */
@Slf4j
@Service("fileChangeRecordService")
@RequiredArgsConstructor
public class FileChangeRecordServiceImpl extends ServiceImpl<FileChangeRecordMapper, FileChangeRecord> implements FileChangeRecordService {

    private final FileChangeRecordMapper fileChangeRecordMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param FileChangeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FileChangeRecord> getPage(PageParam<FileChangeRecord> page, FileChangeRecord param) {
        return fileChangeRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FileChangeRecord getInfoById(String id) {
        return fileChangeRecordMapper.getInfoById(id);
    }

}


