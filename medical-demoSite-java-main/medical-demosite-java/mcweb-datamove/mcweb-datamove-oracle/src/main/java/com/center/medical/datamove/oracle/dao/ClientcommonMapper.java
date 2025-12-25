package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Clientcommon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户公共池表(Clientcommon)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:38
 */
public interface ClientcommonMapper extends BaseMapper<Clientcommon> {

    /**
     * 分页查询[客户公共池表]列表
     *
     * @param page  分页参数
     * @param param Clientcommon查询参数
     * @return 分页数据
     */
    IPage<Clientcommon> getPage(PageParam<Clientcommon> page, @Param("param") Clientcommon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Clientcommon getInfoById(@Param("id") String id);

}
