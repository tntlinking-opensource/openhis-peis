package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientexamitem;
import org.apache.ibatis.annotations.Param;

/**
 * LIS结果(Peispatientexamitem)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 15:00:11
 */
public interface OrPeispatientexamitemMapper extends BaseMapper<OrPeispatientexamitem> {

    /**
     * 分页查询[LIS结果]列表
     *
     * @param page  分页参数
     * @param param Peispatientexamitem查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientexamitem> getPage(PageParam<OrPeispatientexamitem> page, @Param("param") OrPeispatientexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientexamitem getInfoById(@Param("id") String id);

}
