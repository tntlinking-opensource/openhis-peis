package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.KdSaleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 金蝶销售员(KdSaleer)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
public interface KdSaleerMapper extends BaseMapper<KdSaleer> {

    /**
     * 分页查询[金蝶销售员]列表
     *
     * @param page  分页参数
     * @param param KdSaleer查询参数
     * @return 分页数据
     */
    IPage<KdSaleer> getPage(PageParam<KdSaleer> page, @Param("param") KdSaleer param);


}
