package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PacsInspectCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PACS-项目检查费用(PacsInspectCharge)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:50
 */
public interface PacsInspectChargeMapper extends BaseMapper<PacsInspectCharge> {

    /**
     * 分页查询[PACS-项目检查费用]列表
     *
     * @param page  分页参数
     * @param param PacsInspectCharge查询参数
     * @return 分页数据
     */
    IPage<PacsInspectCharge> getList(PageParam<PacsInspectCharge> page, @Param("param") PacsInspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsInspectCharge getInfoById(@Param("id") String id);

    /**
     * 得到相同检查项目的不同收费项目
     * @param inspectId
     * @param strr
     * @return
     */
    List<String> getRepeatItems(@Param("inspectid")String inspectId,@Param("itemId") List<String> strr);
}
