package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeispatientConsultationPic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业问诊签名图片(PeispatientConsultationPic)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:19
 */
public interface PeispatientConsultationPicService extends IService<PeispatientConsultationPic> {

    /**
     * 分页查询[职业问诊签名图片]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientConsultationPic> getPage(PageParam<PeispatientConsultationPic> page, PeispatientConsultationPic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeispatientConsultationPic getInfoById(String id);

}

