package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.HealthResultParam;
import com.center.medical.statistics.bean.vo.HealthResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2023-10-25 09:06:02
 */
public interface HealthResultMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<HealthResultVo> getPage(PageParam<HealthResultVo> page, @Param("param") HealthResultParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);


    /**
     * 导出健康检查结果附表
     * @param param
     * @return
     */
    List<HealthResultVo> getExportData(@Param("param") HealthResultParam param);
}
