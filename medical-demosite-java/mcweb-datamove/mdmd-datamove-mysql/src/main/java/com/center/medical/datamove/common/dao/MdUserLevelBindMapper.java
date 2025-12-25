package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUserLevelBind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 会员当前的等级(MdUserLevelBind)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:19
 */
public interface MdUserLevelBindMapper extends BaseMapper<MdUserLevelBind> {

    /**
     * 分页查询[会员当前的等级]列表
     *
     * @param page  分页参数
     * @param param MdUserLevelBind查询参数
     * @return 分页数据
     */
    IPage<MdUserLevelBind> getPage(PageParam<MdUserLevelBind> page, @Param("param") MdUserLevelBind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userLevelId
     * @return 详情信息
     */
    MdUserLevelBind getInfoById(@Param("id") Long id);

}
