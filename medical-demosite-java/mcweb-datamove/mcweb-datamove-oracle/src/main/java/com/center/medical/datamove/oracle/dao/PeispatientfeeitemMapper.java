package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatientfeeitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者收费项目表(Peispatientfeeitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:29
 */
public interface PeispatientfeeitemMapper extends BaseMapper<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<Peispatientfeeitem> getPage(PageParam<Peispatientfeeitem> page, @Param("param") Peispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientfeeitem getInfoById(@Param("id") String id);

}
