package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrugDisease;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * jc禁忌疾病(MdDrugDisease)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugDiseaseMapper extends BaseMapper<MdDrugDisease> {

    /**
     * 分页查询[jc禁忌疾病]列表
     *
     * @param page  分页参数
     * @param param MdDrugDisease查询参数
     * @return 分页数据
     */
    IPage<MdDrugDisease> getPage(PageParam<MdDrugDisease> page, @Param("param") MdDrugDisease param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugDisease getInfoById(@Param("id") String id);

}
