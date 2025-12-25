package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWhysqzfw;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC危害因素取值范围(MdWhysqzfw)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:32
 */
public interface MdWhysqzfwMapper extends BaseMapper<MdWhysqzfw> {

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param MdWhysqzfw查询参数
     * @return 分页数据
     */
    IPage<MdWhysqzfw> getPage(PageParam<MdWhysqzfw> page, @Param("param") MdWhysqzfw param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWhysqzfw getInfoById(@Param("id") String id);

}
