package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PeispatientConsultationPic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业问诊签名图片(PeispatientConsultationPic)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:02
 */
public interface PeispatientConsultationPicMapper extends BaseMapper<PeispatientConsultationPic> {

    /**
     * 分页查询[职业问诊签名图片]列表
     *
     * @param page  分页参数
     * @param param PeispatientConsultationPic查询参数
     * @return 分页数据
     */
    IPage<PeispatientConsultationPic> getList(PageParam<PeispatientConsultationPic> page, @Param("param") PeispatientConsultationPic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientConsultationPic getInfoById(@Param("id") String id);

}
