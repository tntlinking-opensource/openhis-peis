package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPufftube;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 噗噗管(MdPufftube)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
public interface MdPufftubeMapper extends BaseMapper<MdPufftube> {

    /**
     * 分页查询[噗噗管]列表
     *
     * @param page  分页参数
     * @param param MdPufftube查询参数
     * @return 分页数据
     */
    IPage<MdPufftube> getPage(PageParam<MdPufftube> page, @Param("param") MdPufftube param);


}
