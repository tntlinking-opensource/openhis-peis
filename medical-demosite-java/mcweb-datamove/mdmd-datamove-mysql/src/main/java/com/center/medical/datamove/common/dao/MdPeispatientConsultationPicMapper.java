package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientConsultationPic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业问诊签名图片(MdPeispatientConsultationPic)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
public interface MdPeispatientConsultationPicMapper extends BaseMapper<MdPeispatientConsultationPic> {

    /**
     * 分页查询[职业问诊签名图片]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientConsultationPic查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientConsultationPic> getPage(PageParam<MdPeispatientConsultationPic> page, @Param("param") MdPeispatientConsultationPic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientConsultationPic getInfoById(@Param("id") String id);

}
