package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientfeeitem;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者收费项目表(Peispatientfeeitem)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:55:00
 */
public interface OrPeispatientfeeitemMapper extends BaseMapper<OrPeispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientfeeitem> getPage(PageParam<OrPeispatientfeeitem> page, @Param("param") OrPeispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientfeeitem getInfoById(@Param("id") String id);

}
