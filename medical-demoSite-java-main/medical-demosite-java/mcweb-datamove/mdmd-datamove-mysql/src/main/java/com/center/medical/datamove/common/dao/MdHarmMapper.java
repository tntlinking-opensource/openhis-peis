package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC危害因素(MdHarm)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
public interface MdHarmMapper extends BaseMapper<MdHarm> {

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param MdHarm查询参数
     * @return 分页数据
     */
    IPage<MdHarm> getPage(PageParam<MdHarm> page, @Param("param") MdHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdHarm getInfoById(@Param("id") String id);

}
