package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Createcombo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 套餐表(Createcombo)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:54
 */
public interface CreatecomboMapper extends BaseMapper<Createcombo> {

    /**
     * 分页查询[套餐表]列表
     *
     * @param page  分页参数
     * @param param Createcombo查询参数
     * @return 分页数据
     */
    IPage<Createcombo> getPage(PageParam<Createcombo> page, @Param("param") Createcombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createcombo getInfoById(@Param("id") String id);

}
