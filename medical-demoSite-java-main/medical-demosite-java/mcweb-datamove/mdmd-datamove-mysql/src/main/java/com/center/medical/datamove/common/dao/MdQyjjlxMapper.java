package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdQyjjlx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 企业经济类型(MdQyjjlx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
public interface MdQyjjlxMapper extends BaseMapper<MdQyjjlx> {

    /**
     * 分页查询[企业经济类型]列表
     *
     * @param page  分页参数
     * @param param MdQyjjlx查询参数
     * @return 分页数据
     */
    IPage<MdQyjjlx> getPage(PageParam<MdQyjjlx> page, @Param("param") MdQyjjlx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdQyjjlx getInfoById(@Param("id") String id);

}
