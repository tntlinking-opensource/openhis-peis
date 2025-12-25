package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.dto.CdcExportDto;
import com.center.medical.query.bean.param.CdcPageParam;
import com.center.medical.query.bean.vo.CdcPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CDC职业病直报数据查询(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface CdcMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<CdcPageVo> getList(PageParam<CdcPageVo> page, @Param("param") CdcPageParam param);


    /**
     * 导出自费收费汇总
     *
     * @param param
     * @return
     */
    List<CdcExportDto> getExportData(@Param("param") CdcPageParam param);

}
