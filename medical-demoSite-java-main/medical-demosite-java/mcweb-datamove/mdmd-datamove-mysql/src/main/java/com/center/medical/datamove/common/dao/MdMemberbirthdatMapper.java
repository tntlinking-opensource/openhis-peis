package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdMemberbirthdat;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 会员生日提醒回访表(MdMemberbirthdat)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
public interface MdMemberbirthdatMapper extends BaseMapper<MdMemberbirthdat> {

    /**
     * 分页查询[会员生日提醒回访表]列表
     *
     * @param page  分页参数
     * @param param MdMemberbirthdat查询参数
     * @return 分页数据
     */
    IPage<MdMemberbirthdat> getPage(PageParam<MdMemberbirthdat> page, @Param("param") MdMemberbirthdat param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMemberbirthdat getInfoById(@Param("id") String id);

}
