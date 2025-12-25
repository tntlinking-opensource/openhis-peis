package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.param.AllTcOrderParam;
import com.center.medical.sellcrm.bean.param.ItemOnlineParam;
import com.center.medical.sellcrm.bean.param.PatientDataParam;
import com.center.medical.sellcrm.bean.vo.AllTcOrderVo;
import com.center.medical.sellcrm.bean.vo.ItemOnlineVo;
import com.center.medical.sellcrm.bean.vo.NeedNoticeVo;
import com.center.medical.sellcrm.bean.vo.PatientDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(Createorder)表数据库访问层
 *
 * @author ay
 * @since 2023-03-11 18:02:00
 */
public interface ItemListOnlineMapper extends BaseMapper<Createorder> {

    /**
    * 分页查询[订单表]列表
    *
    * @param page 分页参数
    * @param param Createorder查询参数
    * @return 分页数据
    */
    IPage<ItemOnlineVo> getList(PageParam<ItemOnlineVo> page, @Param("param") ItemOnlineParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Createorder getInfoById(@Param("id") String id);

    /**
     * 导出Excel
     * @param param
     * @return
     */
    List<ItemOnlineVo> getExportData(@Param("param")ItemOnlineParam param);

    /**
     * 获取订单下的所有套餐
     * @param page
     * @param param
     * @return
     */
    IPage<AllTcOrderVo> getAllTcForOrder(PageParam<AllTcOrderVo> page,@Param("param") AllTcOrderParam param);

    /**
     * 获取分组下相应的人员信息
     * @param page
     * @param param
     * @return
     */
    IPage<PatientDataVo> getPatientData(PageParam<PatientDataVo> page,@Param("param") PatientDataParam param);

    /**
     * 线上备单发送短信 查询数据
     * @return
     */
    List<NeedNoticeVo> selectNeedNoticeWechatCodeList();
}
