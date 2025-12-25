package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrugAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 药品分中心映射(MdDrugAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
public interface MdDrugAndFzxMapper extends BaseMapper<MdDrugAndFzx> {

    /**
     * 分页查询[药品分中心映射]列表
     *
     * @param page  分页参数
     * @param param MdDrugAndFzx查询参数
     * @return 分页数据
     */
    IPage<MdDrugAndFzx> getPage(PageParam<MdDrugAndFzx> page, @Param("param") MdDrugAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugAndFzx getInfoById(@Param("id") String id);

}
