package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCardMemberMedical;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 会员卡体检卡关联表(MdCardMemberMedical)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
public interface MdCardMemberMedicalMapper extends BaseMapper<MdCardMemberMedical> {

    /**
     * 分页查询[会员卡体检卡关联表]列表
     *
     * @param page  分页参数
     * @param param MdCardMemberMedical查询参数
     * @return 分页数据
     */
    IPage<MdCardMemberMedical> getPage(PageParam<MdCardMemberMedical> page, @Param("param") MdCardMemberMedical param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCardMemberMedical getInfoById(@Param("id") String id);

}
