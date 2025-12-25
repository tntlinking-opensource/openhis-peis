package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Lung;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS肺功能(Lung)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:39
 */
public interface LungMapper extends BaseMapper<Lung> {

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param Lung查询参数
     * @return 分页数据
     */
    IPage<Lung> getPage(PageParam<Lung> page, @Param("param") Lung param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Lung getInfoById(@Param("id") String id);

}
