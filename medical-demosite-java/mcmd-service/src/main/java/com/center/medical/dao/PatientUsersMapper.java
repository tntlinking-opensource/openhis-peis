package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PatientUsers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检用户账号(PatientUsers)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:40
 */
public interface PatientUsersMapper extends BaseMapper<PatientUsers> {

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param PatientUsers查询参数
     * @return 分页数据
     */
    IPage<PatientUsers> getList(PageParam<PatientUsers> page, @Param("param") PatientUsers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PatientUsers getInfoById(@Param("id") String id);

}
