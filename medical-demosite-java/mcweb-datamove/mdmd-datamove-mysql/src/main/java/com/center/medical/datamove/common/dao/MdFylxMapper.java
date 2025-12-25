package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFylx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC费用类型(MdFylx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
public interface MdFylxMapper extends BaseMapper<MdFylx> {

    /**
     * 分页查询[JC费用类型]列表
     *
     * @param page  分页参数
     * @param param MdFylx查询参数
     * @return 分页数据
     */
    IPage<MdFylx> getPage(PageParam<MdFylx> page, @Param("param") MdFylx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFylx getInfoById(@Param("id") String id);

}
