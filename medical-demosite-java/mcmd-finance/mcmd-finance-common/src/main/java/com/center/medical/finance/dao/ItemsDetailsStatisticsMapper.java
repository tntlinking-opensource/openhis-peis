package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ItemsDetailsStatisticsParam;
import com.center.medical.finance.bean.vo.ItemsDetailsStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报告信息查询(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface ItemsDetailsStatisticsMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<ItemsDetailsStatisticsVo> getList(PageParam<ItemsDetailsStatisticsVo> page, @Param("param") ItemsDetailsStatisticsParam param);

    /**
     * 导出报告信息
     *
     * @param param
     * @return
     */
    List<ItemsDetailsStatisticsVo> getExportData(@Param("param") ItemsDetailsStatisticsParam param);
}
