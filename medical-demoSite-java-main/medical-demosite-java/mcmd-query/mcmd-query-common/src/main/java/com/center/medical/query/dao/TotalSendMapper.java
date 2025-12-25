package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.TotalSendParam;
import com.center.medical.query.bean.vo.TotalSendVo;
import com.center.medical.reception.bean.model.OutsideMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS外送登记结果主表(OutsideMain)表数据库访问层
 *
 * @author ay
 * @since 2023-04-12 11:59:12
 */
public interface TotalSendMapper extends BaseMapper<OutsideMain> {

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param OutsideMain查询参数
     * @return 分页数据
     */
    IPage<TotalSendVo> getList(PageParam<TotalSendVo> page, @Param("param") TotalSendParam param);



    /**
     * 获取合计金额
     * @param param
     * @return
     */
    String amountTo(@Param("param") TotalSendParam param);

    /**
     * 导出外送统计
     * @param param
     * @return
     */
    List<TotalSendVo> getExportData(@Param("param") TotalSendParam param);
}
