package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.KingdeeUploadDataDto;
import com.center.medical.finance.bean.dto.ReceivePaymentDto;
import com.center.medical.finance.bean.model.KdCustomer;
import com.center.medical.finance.bean.param.UploadPeiDataParam;
import com.center.medical.finance.bean.vo.KdCustomerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 金碟账户(KdCustomer)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:42
 */
public interface KdCustomerMapper extends BaseMapper<KdCustomer> {

    /**
     * 分页查询[金碟账户]列表
     *
     * @param page  分页参数
     * @param param KdCustomer查询参数
     * @return 分页数据
     */
    IPage<KdCustomer> getPage(PageParam<KdCustomer> page, @Param("param") KdCustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdCustomer getInfoById(@Param("id") String id);


    /**
     * 获取金蝶客户
     * @param page
     * @param key
     * @return
     */
    IPage<KdCustomerVo> getKingdeeCustomerData(PageParam<KdCustomerVo> page , @Param("key") String key);

    /**
     * 获取上传金蝶星空云数据
     * @return
     */
    List<KingdeeUploadDataDto> getKingdeeUploadData(@Param("param") UploadPeiDataParam param);

    /**
     * 获取收款数据
     * @param baseParam
     * @return
     */
    List<ReceivePaymentDto> getReceivePaymentData(@Param("param") UploadPeiDataParam baseParam);
}
