package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbXyjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血压检测(MdTjbbXyjc)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:04
 */
public interface MdTjbbXyjcMapper extends BaseMapper<MdTjbbXyjc> {

    /**
     * 分页查询[KS血压检测]列表
     *
     * @param page  分页参数
     * @param param MdTjbbXyjc查询参数
     * @return 分页数据
     */
    IPage<MdTjbbXyjc> getPage(PageParam<MdTjbbXyjc> page, @Param("param") MdTjbbXyjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbXyjc getInfoById(@Param("id") String id);

}
