package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientPhotoMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientPhoto;
import com.center.medical.datamove.common.service.MdPeispatientPhotoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者头像(MdPeispatientPhoto)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:13
 */
@Slf4j
@Service("mdPeispatientPhotoService")
@RequiredArgsConstructor
public class MdPeispatientPhotoServiceImpl extends ServiceImpl<MdPeispatientPhotoMapper, MdPeispatientPhoto> implements MdPeispatientPhotoService {

    private final MdPeispatientPhotoMapper mdPeispatientPhotoMapper;

    /**
     * 分页查询[体检者头像]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientPhoto查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientPhoto> getPage(PageParam<MdPeispatientPhoto> page, MdPeispatientPhoto param) {
        return mdPeispatientPhotoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientPhoto getInfoById(String id) {
        return mdPeispatientPhotoMapper.getInfoById(id);
    }

    ;

}


