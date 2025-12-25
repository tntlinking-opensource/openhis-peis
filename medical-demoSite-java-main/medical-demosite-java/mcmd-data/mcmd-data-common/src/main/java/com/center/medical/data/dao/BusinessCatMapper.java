package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BusinessCat;
import com.center.medical.data.bean.param.BusinessCatParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务类型(BusinessCat)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-28 15:40:49
 */
public interface BusinessCatMapper extends BaseMapper<BusinessCat> {

    /**
     * 分页查询[业务类型]列表
     *
     * @param page  分页参数
     * @param param BusinessCat查询参数
     * @return 分页数据
     */
    IPage<BusinessCat> getPage(PageParam<BusinessCat> page, @Param("param") BusinessCatParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    BusinessCat getInfoById(@Param("id") Long id);

    List<BusinessCat> getList(@Param("param") BusinessCat param);

    /**
     * 导出业务类型
     * @param param
     * @return
     */
    List<BusinessCat> getExportData(@Param("param") BusinessCatParam param);
}
