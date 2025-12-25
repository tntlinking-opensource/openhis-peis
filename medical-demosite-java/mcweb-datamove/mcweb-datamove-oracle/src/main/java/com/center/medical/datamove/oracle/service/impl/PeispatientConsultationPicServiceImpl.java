package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientConsultationPicMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientConsultationPic;
import com.center.medical.datamove.oracle.service.PeispatientConsultationPicService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业问诊签名图片(PeispatientConsultationPic)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:20
 */
@Slf4j
@Service("peispatientConsultationPicService")
@RequiredArgsConstructor
public class PeispatientConsultationPicServiceImpl extends ServiceImpl<PeispatientConsultationPicMapper, PeispatientConsultationPic> implements PeispatientConsultationPicService {

    private final PeispatientConsultationPicMapper peispatientConsultationPicMapper;

    /**
     * 分页查询[职业问诊签名图片]列表
     *
     * @param page  分页参数
     * @param param PeispatientConsultationPic查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientConsultationPic> getPage(PageParam<PeispatientConsultationPic> page, PeispatientConsultationPic param) {
        return peispatientConsultationPicMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientConsultationPic getInfoById(String id) {
        return peispatientConsultationPicMapper.getInfoById(id);
    }

    ;

}


