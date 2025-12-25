package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeisDydCtSeq;
import com.center.medical.common.utils.page.PageParam;

import java.util.Date;

/**
 * 体检者导引单CT排序(PeisDydCtSeq)服务接口
 *
 * @author ay
 * @since 2023-09-01 18:47:31
 */
public interface PeisDydCtSeqService extends IService<PeisDydCtSeq> {

    /**
     * 分页查询[体检者导引单CT排序]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeisDydCtSeq> getPage(PageParam<PeisDydCtSeq> page, PeisDydCtSeq param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisDydCtSeq getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    PeisDydCtSeq getByPatientcode(String patientCode);

    /**
     * 获取当天最后的排序,没有返回空
     * @param date
     * @return
     */
    Integer getLastSeq(Date date);
}

