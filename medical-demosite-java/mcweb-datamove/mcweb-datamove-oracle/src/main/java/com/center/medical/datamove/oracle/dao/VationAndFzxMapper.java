package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.VationAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (VationAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:50
 */
public interface VationAndFzxMapper extends BaseMapper<VationAndFzx> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VationAndFzx查询参数
     * @return 分页数据
     */
    IPage<VationAndFzx> getPage(PageParam<VationAndFzx> page, @Param("param") VationAndFzx param);


}
