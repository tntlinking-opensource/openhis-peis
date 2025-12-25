package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.BkPatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.BkPatientParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者(BkPatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
public interface BkPatientMapper extends BaseMapper<BkPatient> {

    /**
     * 分页查询[体检者]列表
     *
     * @param page  分页参数
     * @param param BkPatient查询参数
     * @return 分页数据
     */
    IPage<BkPatient> getList(PageParam<BkPatient> page, @Param("param") BkPatientParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BkPatient getInfoById(@Param("id") String id);

}
