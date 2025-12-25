package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.ChargeInfoParam;
import com.center.medical.query.bean.vo.ChargeInfoPageVo;
import com.center.medical.query.bean.vo.FinanceAmountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface ChargeInfoMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<ChargeInfoPageVo> getList(PageParam<ChargeInfoPageVo> page, @Param("param") ChargeInfoParam param);


    /**
     * 导出收费信息查询
     *
     * @param param
     * @return
     */
    List<ChargeInfoPageVo> getExportData(@Param("param") ChargeInfoParam param);

    /**
     * 获取合计数据
     * @param param
     * @return
     */
    FinanceAmountVo financeCountAmount(@Param("param") ChargeInfoParam param);
}
