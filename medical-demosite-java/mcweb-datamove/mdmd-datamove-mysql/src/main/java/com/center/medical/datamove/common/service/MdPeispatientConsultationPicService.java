package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeispatientConsultationPic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业问诊签名图片(MdPeispatientConsultationPic)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
public interface MdPeispatientConsultationPicService extends IService<MdPeispatientConsultationPic> {

    /**
     * 分页查询[职业问诊签名图片]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientConsultationPic> getPage(PageParam<MdPeispatientConsultationPic> page, MdPeispatientConsultationPic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientConsultationPic getInfoById(String id);

}

