package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdAttachFileMapper;
import com.center.medical.datamove.common.bean.model.MdAttachFile;
import com.center.medical.datamove.common.service.MdAttachFileService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 上传文件记录表(MdAttachFile)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
@Slf4j
@Service("mdAttachFileService")
@RequiredArgsConstructor
public class MdAttachFileServiceImpl extends ServiceImpl<MdAttachFileMapper, MdAttachFile> implements MdAttachFileService {

    private final MdAttachFileMapper mdAttachFileMapper;

    /**
     * 分页查询[上传文件记录表]列表
     *
     * @param page  分页参数
     * @param param MdAttachFile查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdAttachFile> getPage(PageParam<MdAttachFile> page, MdAttachFile param) {
        return mdAttachFileMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fileId
     * @return 详情信息
     */
    @Override
    public MdAttachFile getInfoById(String id) {
        return mdAttachFileMapper.getInfoById(id);
    }

}


