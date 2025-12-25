package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Pufftube;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 噗噗管(Pufftube)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:05
 */
public interface PufftubeMapper extends BaseMapper<Pufftube> {

    /**
     * 分页查询[噗噗管]列表
     *
     * @param page  分页参数
     * @param param Pufftube查询参数
     * @return 分页数据
     */
    IPage<Pufftube> getList(PageParam<Pufftube> page, @Param("param") Pufftube param);


}
