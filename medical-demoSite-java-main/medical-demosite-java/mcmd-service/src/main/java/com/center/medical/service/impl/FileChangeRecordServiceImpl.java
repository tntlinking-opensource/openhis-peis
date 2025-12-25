package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FileChangeRecordMapper;
import com.center.medical.bean.model.FileChangeRecord;
import com.center.medical.service.FileChangeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 记录有文件改变的文件目录(FileChangeRecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:21
 */
@Slf4j
@Service("fileChangeRecordService")
@RequiredArgsConstructor
public class FileChangeRecordServiceImpl extends ServiceImpl<FileChangeRecordMapper, FileChangeRecord> implements FileChangeRecordService {

    private final FileChangeRecordMapper fileChangeRecordMapper;

    /**
     * 分页查询[记录有文件改变的文件目录]列表
     *
     * @param page  分页参数
     * @param param FileChangeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FileChangeRecord> getList(PageParam<FileChangeRecord> page, FileChangeRecord param) {
        return fileChangeRecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FileChangeRecord getInfoById(String id) {
        return fileChangeRecordMapper.getInfoById(id);
    }

    ;

}

