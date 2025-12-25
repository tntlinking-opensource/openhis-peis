package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientConsultationPicMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientConsultationPic;
import com.center.medical.datamove.common.service.MdPeispatientConsultationPicService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业问诊签名图片(MdPeispatientConsultationPic)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
@Slf4j
@Service("mdPeispatientConsultationPicService")
@RequiredArgsConstructor
public class MdPeispatientConsultationPicServiceImpl extends ServiceImpl<MdPeispatientConsultationPicMapper, MdPeispatientConsultationPic> implements MdPeispatientConsultationPicService {

    private final MdPeispatientConsultationPicMapper mdPeispatientConsultationPicMapper;

    /**
     * 分页查询[职业问诊签名图片]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientConsultationPic查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientConsultationPic> getPage(PageParam<MdPeispatientConsultationPic> page, MdPeispatientConsultationPic param) {
        return mdPeispatientConsultationPicMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientConsultationPic getInfoById(String id) {
        return mdPeispatientConsultationPicMapper.getInfoById(id);
    }

}


