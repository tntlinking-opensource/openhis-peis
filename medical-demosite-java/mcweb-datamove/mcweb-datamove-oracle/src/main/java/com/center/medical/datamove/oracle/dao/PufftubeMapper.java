package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Pufftube;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Pufftube)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:40
 */
public interface PufftubeMapper extends BaseMapper<Pufftube> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Pufftube查询参数
     * @return 分页数据
     */
    IPage<Pufftube> getPage(PageParam<Pufftube> page, @Param("param") Pufftube param);


}
