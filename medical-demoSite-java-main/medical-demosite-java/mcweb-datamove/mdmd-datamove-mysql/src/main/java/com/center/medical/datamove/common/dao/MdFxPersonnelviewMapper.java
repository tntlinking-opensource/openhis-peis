package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxPersonnelview;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 综合分析-人员一览表(MdFxPersonnelview)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxPersonnelviewMapper extends BaseMapper<MdFxPersonnelview> {

    /**
     * 分页查询[综合分析-人员一览表]列表
     *
     * @param page  分页参数
     * @param param MdFxPersonnelview查询参数
     * @return 分页数据
     */
    IPage<MdFxPersonnelview> getPage(PageParam<MdFxPersonnelview> page, @Param("param") MdFxPersonnelview param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxPersonnelview getInfoById(@Param("id") String id);

}
