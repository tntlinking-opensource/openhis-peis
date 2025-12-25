package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUserLevel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 会员等级表(MdUserLevel)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:18
 */
public interface MdUserLevelMapper extends BaseMapper<MdUserLevel> {

    /**
     * 分页查询[会员等级表]列表
     *
     * @param page  分页参数
     * @param param MdUserLevel查询参数
     * @return 分页数据
     */
    IPage<MdUserLevel> getPage(PageParam<MdUserLevel> page, @Param("param") MdUserLevel param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键levelId
     * @return 详情信息
     */
    MdUserLevel getInfoById(@Param("id") Integer id);

}
