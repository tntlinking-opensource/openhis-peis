package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检用户账号(MdUser)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:15
 */
public interface MdUserMapper extends BaseMapper<MdUser> {

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param MdUser查询参数
     * @return 分页数据
     */
    IPage<MdUser> getPage(PageParam<MdUser> page, @Param("param") MdUser param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    MdUser getInfoById(@Param("id") String id);

}
