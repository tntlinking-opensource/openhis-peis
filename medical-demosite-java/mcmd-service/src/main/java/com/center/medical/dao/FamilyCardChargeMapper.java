package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FamilyCardCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 家庭卡充值记录(FamilyCardCharge)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:31
 */
public interface FamilyCardChargeMapper extends BaseMapper<FamilyCardCharge> {

    /**
     * 分页查询[家庭卡充值记录]列表
     *
     * @param page  分页参数
     * @param param FamilyCardCharge查询参数
     * @return 分页数据
     */
    IPage<FamilyCardCharge> getList(PageParam<FamilyCardCharge> page, @Param("param") FamilyCardCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FamilyCardCharge getInfoById(@Param("id") String id);

}
