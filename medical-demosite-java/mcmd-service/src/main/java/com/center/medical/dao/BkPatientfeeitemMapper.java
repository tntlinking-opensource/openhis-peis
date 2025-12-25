package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.BkPatientfeeitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者收费项目(BkPatientfeeitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:15
 */
public interface BkPatientfeeitemMapper extends BaseMapper<BkPatientfeeitem> {

    /**
     * 分页查询[体检者收费项目]列表
     *
     * @param page  分页参数
     * @param param BkPatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<BkPatientfeeitem> getList(PageParam<BkPatientfeeitem> page, @Param("param") BkPatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BkPatientfeeitem getInfoById(@Param("id") String id);

}
