package com.center.medical.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.enterprise.bean.model.Createorder;
import com.center.medical.enterprise.bean.param.CreateOrderInfoDataParam;
import com.center.medical.enterprise.bean.param.CreateOrderInfoItemParam;
import com.center.medical.enterprise.bean.param.GetOrderListParam;
import com.center.medical.enterprise.bean.param.PeipatientDataParam;
import com.center.medical.enterprise.bean.vo.*;
import com.center.medical.enterprise.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表(MdCreateorder)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
public interface CreateorderMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param MdCreateorder查询参数
     * @return 分页数据
     */
    IPage<Createorder> getPage(PageParam<Createorder> page, @Param("param") Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(@Param("id") String id);

    /**
     * 获取订单详情
     * @param page
     * @param param
     * @return
     */
    IPage<CreateOrderInfoDataVo> getInfoListData(PageParam<CreateOrderInfoDataVo> page,@Param("param") CreateOrderInfoDataParam param);

    /**
     * 获取套餐详情
     * @param page
     * @param id
     * @return
     */
    IPage<CreateOrderInfoMealVo> getInfoMealData(PageParam<CreateOrderInfoMealVo> page,@Param("id") String id);

    /**
     * 获取危害因素名称
     * @param ids
     * @return
     */
    String getHarmByIds(@Param("ids") String[] ids);

    /**
     * 获取套餐详情
     * @param page
     * @param param
     * @return
     */
    IPage<CreateOrderInfoItemlVo> getInfoItemData(PageParam<CreateOrderInfoItemlVo> page,@Param("param") CreateOrderInfoItemParam param);

    /**
     * 获取订单列表
     * @param page
     * @param param
     * @return
     */
    IPage<GetOrderListVo> getOrderList(PageParam<GetOrderListVo> page,@Param("param") GetOrderListParam param);

    /**
     * 获取体检者数据
     * @param page
     * @param param
     * @return
     */
    IPage<PeipatientDataVo> getPeipatientDataList(PageParam<PeipatientDataVo> page,@Param("param") PeipatientDataParam param);
}
