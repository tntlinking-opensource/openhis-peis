package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.OrderPaDataParam;
import com.center.medical.reception.bean.vo.*;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.param.DbOrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 前台-备单数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
public interface OrderMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询备单订单列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<BdOrderVo> getPage(PageParam<Createorder> page, @Param("param") DbOrderParam param);

    /**
     * 获取分组下相应的人员信息
     * @param page
     * @param param
     * @return
     */
    IPage<OrderPaDataVo> getPatientData(PageParam<OrderPaDataVo> page , @Param("param") OrderPaDataParam param);

    /**
     * 获取分页统计数据
     * @param param
     * @return
     */
    StatisticsVo getStatistics( @Param("param")DbOrderParam param);

    /**
     * 获取订单导出数据
     * @param param
     * @return
     */
    List<BdOrderVo> getExportData(@Param("param")DbOrderParam param);

    /**
     * 获取应急导引单数据
     * @param id
     * @return
     */
    List<ExportGuidanceListVo> exportGuidanceList(@Param("id") String id);

    /**
     * 查询该订单号下的未发预约短信的体检者
     * @param ddh
     * @return
     */
    List<Peispatient> getSMSPeiByByDddh(@Param("ddh") String ddh);

    /**
     * 获取分组下相应的人员信息(不分页)
     * @param param
     * @return
     */
    List<OrderPaDataVo> getPatientDataList( @Param("param") OrderPaDataParam param);

    /**
     * 校正体检者
     * @param idPayway
     * @return
     */
    List<CheckPeispatientVo> checkPeispatient( @Param("idPayway") String idPayway);
}
