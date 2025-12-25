package com.center.medical.sync.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncFile;
import com.center.medical.sync.bean.param.SyncFileParam;
import com.center.medical.sync.dao.SyncFileMapper;
import com.center.medical.sync.service.SyncFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步文件操作(SyncFile)服务实现类
 *
 * @author makejava
 * @since 2023-09-12 10:25:46
 */
@Slf4j
@Service("syncFileService")
@RequiredArgsConstructor
public class SyncFileServiceImpl extends ServiceImpl<SyncFileMapper, SyncFile> implements SyncFileService {

    private final SyncFileMapper syncFileMapper;

    /**
     * 分页查询[同步文件操作]列表
     *
     * @param page  分页参数
     * @param param SyncImage查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SyncFile> getPage(PageParam<SyncFile> page, SyncFile param) {
        return syncFileMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SyncFile getInfoById(Long id) {
        return syncFileMapper.getInfoById(id);
    }

    /**
     * 获取最新的一条数据
     *
     * @param imgUrl 图片地址
     * @param list   同步状态
     * @return
     */
    @Override
    public SyncFile getLastOne(String imgUrl, List<Integer> list) {
        return syncFileMapper.getLastOne(new SyncFileParam("", imgUrl, list));
    }

}

