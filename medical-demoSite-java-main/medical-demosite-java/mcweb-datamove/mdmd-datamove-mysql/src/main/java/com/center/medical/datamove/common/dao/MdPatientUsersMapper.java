package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPatientUsers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检用户账号(MdPatientUsers)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
public interface MdPatientUsersMapper extends BaseMapper<MdPatientUsers> {

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param MdPatientUsers查询参数
     * @return 分页数据
     */
    IPage<MdPatientUsers> getPage(PageParam<MdPatientUsers> page, @Param("param") MdPatientUsers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPatientUsers getInfoById(@Param("id") String id);

}
