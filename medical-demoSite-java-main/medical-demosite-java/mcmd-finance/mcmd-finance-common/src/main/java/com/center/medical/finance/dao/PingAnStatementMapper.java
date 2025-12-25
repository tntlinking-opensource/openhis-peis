package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.PatientListParam;
import com.center.medical.finance.bean.param.PingAnPageParam;
import com.center.medical.finance.bean.vo.PatientListVo;
import com.center.medical.finance.bean.vo.PingAnPageVo;
import com.center.medical.sellcrm.bean.model.Createorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(Createorder)表数据库访问层
 *
 * @author ay
 * @since 2023-04-03 14:19:37
 */
public interface PingAnStatementMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    IPage<PingAnPageVo> getList(PageParam<PingAnPageVo> page, @Param("param") PingAnPageParam param);


    /**
     * 获取体检者数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PatientListVo> getPatientListData(PageParam<PatientListVo> page, @Param("param") PatientListParam param);

    /**
     * 导出体检人员
     * @param param
     * @return
     */
    List<PatientListVo> exportOrderPatient(@Param("param") PatientListParam param);
}
