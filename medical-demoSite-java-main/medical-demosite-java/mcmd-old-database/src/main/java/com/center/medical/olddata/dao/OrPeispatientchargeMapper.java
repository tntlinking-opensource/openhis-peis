package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientcharge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者缴费表(Peispatientcharge)数据库访问层
 *
 * @author ay
 * @since 2023-11-10 14:59:30
 */
public interface OrPeispatientchargeMapper extends BaseMapper<OrPeispatientcharge> {

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param Peispatientcharge查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientcharge> getPage(PageParam<OrPeispatientcharge> page, @Param("param") OrPeispatientcharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientcharge getInfoById(@Param("id") String id);

    /**
     * 通过体检号查询
     * @param oldPatientCode
     * @return
     */
    List<OrPeispatientcharge> getByPatientCode(@Param("patientCode") String oldPatientCode);
}
