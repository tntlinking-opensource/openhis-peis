package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsInspectCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-项目检查费用(MdPacsInspectCharge)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
public interface MdPacsInspectChargeMapper extends BaseMapper<MdPacsInspectCharge> {

    /**
     * 分页查询[PACS-项目检查费用]列表
     *
     * @param page  分页参数
     * @param param MdPacsInspectCharge查询参数
     * @return 分页数据
     */
    IPage<MdPacsInspectCharge> getPage(PageParam<MdPacsInspectCharge> page, @Param("param") MdPacsInspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsInspectCharge getInfoById(@Param("id") String id);

}
