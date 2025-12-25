package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientConsultationPicMapper;
import com.center.medical.bean.model.PeispatientConsultationPic;
import com.center.medical.service.PeispatientConsultationPicService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业问诊签名图片(PeispatientConsultationPic)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:02
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
    public IPage<PeispatientConsultationPic> getList(PageParam<PeispatientConsultationPic> page, PeispatientConsultationPic param) {
        return peispatientConsultationPicMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientConsultationPic getInfoById(String id) {
        return peispatientConsultationPicMapper.getInfoById(id);
    }

}

