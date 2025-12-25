package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Createmeal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 普通套餐表(Createmeal)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:57
 */
public interface CreatemealMapper extends BaseMapper<Createmeal> {

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param Createmeal查询参数
     * @return 分页数据
     */
    IPage<Createmeal> getPage(PageParam<Createmeal> page, @Param("param") Createmeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createmeal getInfoById(@Param("id") String id);

}
