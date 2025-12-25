package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUserauthcode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用户授权码(MdUserauthcode)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:26
 */
public interface MdUserauthcodeMapper extends BaseMapper<MdUserauthcode> {

    /**
     * 分页查询[用户授权码]列表
     *
     * @param page  分页参数
     * @param param MdUserauthcode查询参数
     * @return 分页数据
     */
    IPage<MdUserauthcode> getPage(PageParam<MdUserauthcode> page, @Param("param") MdUserauthcode param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUserauthcode getInfoById(@Param("id") String id);

}
