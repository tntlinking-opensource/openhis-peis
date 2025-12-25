package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrugstorePrescribe;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 开药记录(MdDrugstorePrescribe)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugstorePrescribeMapper extends BaseMapper<MdDrugstorePrescribe> {

    /**
     * 分页查询[开药记录]列表
     *
     * @param page  分页参数
     * @param param MdDrugstorePrescribe查询参数
     * @return 分页数据
     */
    IPage<MdDrugstorePrescribe> getPage(PageParam<MdDrugstorePrescribe> page, @Param("param") MdDrugstorePrescribe param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugstorePrescribe getInfoById(@Param("id") String id);

}
