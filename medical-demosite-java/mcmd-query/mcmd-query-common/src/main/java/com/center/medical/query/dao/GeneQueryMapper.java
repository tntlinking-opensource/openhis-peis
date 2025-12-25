package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.GeneQueryParam;
import com.center.medical.query.bean.vo.GeneQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2023-10-25 09:06:02
 */
public interface GeneQueryMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<GeneQueryVo> getPage(PageParam<GeneQueryVo> page, @Param("param") GeneQueryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 导出新产品数据查询
     * @param param
     * @return
     */
    List<GeneQueryVo> getExportData(@Param("param") GeneQueryParam param);
}
