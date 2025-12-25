package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.TotalAddParam;
import com.center.medical.query.bean.vo.TotalAddVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 加项情况查询(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface TotalAddMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<TotalAddVo> getList(PageParam<TotalAddVo> page, @Param("param") TotalAddParam param);


    /**
     * 海关需求 增加一列非统收的加项总金额
     *
     * @param patientcode
     * @return
     */
    Double getTotalAddPrice(@Param("patientcode") String patientcode);


    /**
     * 导出加项查询数据
     *
     * @param param
     * @return
     */
    List<TotalAddVo> getExportData(@Param("param") TotalAddParam param);

    /**
     * 获取分页合计
     * @param param
     * @return
     */
    Double getPageTotal(@Param("param") TotalAddParam param);
}
