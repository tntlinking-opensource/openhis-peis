package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientPhotoMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientPhoto;
import com.center.medical.datamove.oracle.service.PeispatientPhotoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者头像(PeispatientPhoto)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:22
 */
@Slf4j
@Service("peispatientPhotoService")
@RequiredArgsConstructor
public class PeispatientPhotoServiceImpl extends ServiceImpl<PeispatientPhotoMapper, PeispatientPhoto> implements PeispatientPhotoService {

    private final PeispatientPhotoMapper peispatientPhotoMapper;

    /**
     * 分页查询[体检者头像]列表
     *
     * @param page  分页参数
     * @param param PeispatientPhoto查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientPhoto> getPage(PageParam<PeispatientPhoto> page, PeispatientPhoto param) {
        return peispatientPhotoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientPhoto getInfoById(String id) {
        return peispatientPhotoMapper.getInfoById(id);
    }

}


