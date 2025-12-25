package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFileChangeRecordMapper;
import com.center.medical.datamove.common.bean.model.MdFileChangeRecord;
import com.center.medical.datamove.common.service.MdFileChangeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 记录有文件改变的文件目录(MdFileChangeRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
@Slf4j
@Service("mdFileChangeRecordService")
@RequiredArgsConstructor
public class MdFileChangeRecordServiceImpl extends ServiceImpl<MdFileChangeRecordMapper, MdFileChangeRecord> implements MdFileChangeRecordService {

    private final MdFileChangeRecordMapper mdFileChangeRecordMapper;

    /**
     * 分页查询[记录有文件改变的文件目录]列表
     *
     * @param page  分页参数
     * @param param MdFileChangeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFileChangeRecord> getPage(PageParam<MdFileChangeRecord> page, MdFileChangeRecord param) {
        return mdFileChangeRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFileChangeRecord getInfoById(String id) {
        return mdFileChangeRecordMapper.getInfoById(id);
    }

}


